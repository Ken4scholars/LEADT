// $Id: UMLCallEventOperationComboBoxModel.java 15598 2008-08-21 14:21:24Z penyaskito $
// Copyright (c) 2008 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.core.propertypanels.ui;

import java.util.ArrayList;
import java.util.Collection;

import org.argouml.model.Model;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.ui.UMLComboBoxModel2;

/**
 *
 * @author oliver.heyden@gentleware.de
 */
public class UMLCallEventOperationComboBoxModel extends UMLComboBoxModel2 {
    /**
     * The constructor.
     */
    public UMLCallEventOperationComboBoxModel() {
        super("operation", true);
    }

    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel2#buildModelList()
     */
    protected void buildModelList() {
        Object target = TargetManager.getInstance().getModelTarget();
        Collection ops = new ArrayList();
        if (Model.getFacade().isACallEvent(target)) {
            Object ns = Model.getFacade().getNamespace(target);
            if (Model.getFacade().isANamespace(ns)) {
                Collection classifiers =
                    Model.getModelManagementHelper().getAllModelElementsOfKind(
                            ns,
                            Model.getMetaTypes().getClassifier());
                for (Object classifier : classifiers) {
                    ops.addAll(Model.getFacade().getOperations(classifier));
                }
                
                // TODO: getAllModelElementsOfKind should probably do this
                // processing of imported elements automatically
                for (Object importedElem : Model.getModelManagementHelper()
                        .getAllImportedElements(ns)) {
                    if (Model.getFacade().isAClassifier(importedElem)) {
                        ops.addAll(Model.getFacade()
                                .getOperations(importedElem));
                    }
                }
            }
        }
        setElements(ops);
    }

    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel2#getSelectedModelElement()
     */
    protected Object getSelectedModelElement() {
        Object target = TargetManager.getInstance().getModelTarget();
        if (Model.getFacade().isACallEvent(target)) {
            return Model.getFacade().getOperation(target);
        }
        return null;
    }

    /*
     * @see org.argouml.uml.ui.UMLComboBoxModel2#isValidElement(java.lang.Object)
     */
    protected boolean isValidElement(Object element) {
        Object target = TargetManager.getInstance().getModelTarget();
        if (Model.getFacade().isACallEvent(target)) {
            return element == Model.getFacade().getOperation(target);
        }
        return false;
    }
}