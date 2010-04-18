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
package featureide.fm.core;

import org.eclipse.core.resources.IFile;

/**
 * encapsulates a grammar file. handles markers and such
 * 
 * (could already be done on the CORE level)
 * 
 * @author Christian Kaestner
 */
public class GrammarFile extends ModelMarkerHandler {

	private IFile file;

	public GrammarFile(IFile file) {
		super(file);
		this.file=file;
	}

	public IFile getResource() {
		return file;
	}

}
