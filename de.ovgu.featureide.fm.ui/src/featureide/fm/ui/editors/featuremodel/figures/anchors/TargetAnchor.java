/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2009  FeatureIDE Team, University of Magdeburg
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package featureide.fm.ui.editors.featuremodel.figures.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

import featureide.fm.core.Feature;
import featureide.fm.ui.editors.FeatureUIHelper;

/**
 * Used to get the point where all connections from child features end.
 * 
 * @author Thomas Thuem
 */
public class TargetAnchor extends AbstractConnectionAnchor {

	private Feature model;

	public TargetAnchor(IFigure owner, Feature model) {
		super(owner);
		this.model = model;
	}
	
	public Point getLocation(Point reference) {
		Point ref = FeatureUIHelper.getTargetLocation(model);
		getOwner().translateToAbsolute(ref);
		return ref;
	}
	
}
