package de.ovgu.cide.fm.guidsl;

import java.util.Collections;
import java.util.Set;

import org.eclipse.swt.graphics.RGB;

import de.ovgu.cide.features.IFeature;
import de.ovgu.cide.features.IFeatureWithID;
import featureide.fm.core.Feature;

public class FeatureAdapter implements IFeatureWithID {

	private Feature feature;
	private GuidslFeatureModelWrapper model;

	public FeatureAdapter(Feature feature, GuidslFeatureModelWrapper model) {
		this.feature = feature;
		this.model = model;
	}

	public long getId() {
		return model.extraAttributeStorage.getFeatureId(feature);
	}

	public String getName() {
		return feature.getName();
	}

	public RGB getRGB() {
		return model.extraAttributeStorage.getFeatureColor(feature);
	}

	public Set<IFeature> getRequiredFeatures() {
		return Collections.EMPTY_SET;
	}

	public boolean isVisible() {
		return model.extraAttributeStorage.isFeatureVisible(feature);
	}

	public void setName(String name) throws UnsupportedOperationException {
		// feature.setName(name);
		throw new UnsupportedOperationException();
	}

	public void setRGB(RGB color) throws UnsupportedOperationException {
		model.extraAttributeStorage.setFeatureColor(feature, color);
	}

	public void setVisible(boolean isVisible)
			throws UnsupportedOperationException {
		model.extraAttributeStorage.setFeatureVisibile(feature, isVisible);
	}

	public int compareTo(IFeature o) {
		return getName().compareTo(o.getName());
	}

	@Override
	public int hashCode() {
		long value = getId();
		return (int) (value ^ (value >>> 32));// see Long.hashCode;
	}

	@Override
	/** 
	 * two features are the same if they have the same ID (no comparison on the name!)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof FeatureAdapter)
			return getId() == ((FeatureAdapter) obj).getId();
		if (obj instanceof Feature)
			return getId() == model.extraAttributeStorage
					.getFeatureId((Feature) obj);
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Feature:" + getName() + "[" + hashCode() + "]";
	}

	public boolean canSetName() {
		return false;
	}

	public boolean canSetRGB() {
		return true;
	}

	public boolean canSetVisible() {
		return true;
	}

	/**
	 * to be used only within this plugin!
	 * 
	 * @return
	 */
	public Feature getInternalFeature() {
		return feature;
	}

}
