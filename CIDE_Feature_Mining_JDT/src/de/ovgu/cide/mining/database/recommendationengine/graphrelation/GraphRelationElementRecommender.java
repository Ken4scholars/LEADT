package de.ovgu.cide.mining.database.recommendationengine.graphrelation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.ovgu.cide.features.IFeature;
import de.ovgu.cide.mining.database.model.AElement;
import de.ovgu.cide.mining.database.model.AICategories;
import de.ovgu.cide.mining.database.model.ARelationKind;
import de.ovgu.cide.mining.database.recommendationengine.AAbstractElementRecommender;
import de.ovgu.cide.mining.database.recommendationengine.ARecommendationContext;

public class GraphRelationElementRecommender extends
		AAbstractElementRecommender {

	public GraphRelationElementRecommender() {
		super();

	}

	public Map<AElement, ARecommendationContext> getRecommendations(
			AElement element, IFeature color) {

		Map<AElement, ARecommendationContext> recommendations = new HashMap<AElement, ARecommendationContext>();

		Set<ARelationKind> validTransponseRelations = ARelationKind
				.getAllRelations(element.getCategory(), true, false);

		// ADDED AFTER EVALUATION
		validTransponseRelations.addAll(ARelationKind.getAllRelations(element
				.getCategory(), true, true));

		for (AICategories cat : element.getSubCategories()) {
			validTransponseRelations.addAll(ARelationKind.getAllRelations(cat,
					true, false));
			// ADDED AFTER EVALUATION
			validTransponseRelations.addAll(ARelationKind.getAllRelations(cat,
					true, true));
		}

		// check all relations
		for (ARelationKind tmpTransRelation : validTransponseRelations) {
			try {

				// get the forward elements
				Set<AElement> forwardElements = AC.getRange(element,
						tmpTransRelation);
				Set<AElement> validRecommendationElements = new HashSet<AElement>();

				int forwardColorElements = 0;
				int forwardNonColorElements = 0;

				// check how much of them already in color
				// int validRecommendationCount = 0;
				for (AElement forwardElement : forwardElements) {

					if (isInColor(forwardElement, color)) {
						forwardColorElements++;
						continue;
					}

					if (isInNonColor(forwardElement, color)) {
						forwardNonColorElements++;
						continue;
					}

					if (isValidRecommendation(forwardElement, color))
						validRecommendationElements.add(forwardElement);

				}

				// if they are all already in color or marked as not color
				// elements, skip to next relation
				if (validRecommendationElements.size() == 0)
					continue;

				// int invalidForwardRecommendations = forwardElements.size() -
				// validRecommendationElements.size();

				for (AElement validForwardElement : validRecommendationElements) {

					// get backward elements for transpose
					Set<AElement> backwardElements = AC.getRange(
							validForwardElement, tmpTransRelation
									.getInverseRelation());

					// calc how much of backward is already in color
					int backwardColorElements = 0;
					int backwardNonColorElements = 0;

					for (AElement backwardElement : backwardElements) {

						if (isInColor(backwardElement, color)) {
							backwardColorElements++;
							continue;
						}

						if (isInNonColor(backwardElement, color)) {
							backwardNonColorElements++;
							continue;
						}

					}

					// System.out.println(element.getFullName());

					// calc color degree
					double colorDegree = ((double) (1 + forwardColorElements) / (double) forwardElements
							.size())
							* ((double) backwardColorElements / (double) backwardElements
									.size());

					// calc non color degree
					double nonColorDegree = ((double) (1 + forwardNonColorElements) / (double) forwardElements
							.size())
							* ((double) backwardNonColorElements / (double) backwardElements
									.size());

					double degree = colorDegree - nonColorDegree;

					if (degree <= 0)
						continue;

					// add / merge recommendation with alreay available ones
					ARecommendationContext newContext = new ARecommendationContext(
							element, tmpTransRelation.getName(),
							getRecommendationType(), degree);
					ARecommendationContext oldContext = recommendations
							.get(validForwardElement);

					if (oldContext != null) {
						newContext = new ARecommendationContext(newContext,
								oldContext, getRecommendationType());
					}
					recommendations.put(validForwardElement, newContext);

				}

			} catch (Exception e) {

			}
		}

		return recommendations;
	}

	@Override
	public String getRecommendationType() {
		return "GR";
	}

}
