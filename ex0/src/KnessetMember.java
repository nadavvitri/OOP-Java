/**
 * Created by nadav vitri on 12/03/2017.
 */
public class KnessetMember {
    static double knessetMembersEnthusiasmThreshold = 5;
    static int noEnthusiasm = 0;
    String knessetMemberFirstName, knessetMemberLastName;
    double socialTendency, economyTendency, politicalTendency;
    int knessetMemberSurveyThreshold;

    /**
     * Creates a new KnessetMember with the given characteristics.
     * @param knessetMemberFirstName The first name of the KnessetMember.
     * @param knessetMemberLastName The last name of the KnessetMember.
     * @param socialTendency The weight the KnessetMember assigns to the social aspects of laws.
     * @param economyTendency The weight the KnessetMember assigns to the economy aspects of laws.
     * @param politicalTendency The weight the KnessetMember assigns to the political aspects of laws.
     * @param knessetMemberSurveyThreshold The minimal public support a law must have for this KnessetMember
     *                                    to join it.
     */
    KnessetMember(String knessetMemberFirstName, String knessetMemberLastName,double socialTendency,
                  double economyTendency, double politicalTendency, int knessetMemberSurveyThreshold){
        this.knessetMemberFirstName = knessetMemberFirstName;
        this.knessetMemberLastName = knessetMemberLastName;
        this.socialTendency = socialTendency;
        this.economyTendency = economyTendency;
        this.politicalTendency = politicalTendency;
        this.knessetMemberSurveyThreshold = knessetMemberSurveyThreshold;

    }

    /**
     * Returns a string representation of the KnessetMember, which is a sequence of its first name,
     * last name and title separated by a single white space. For example, if the KnessetMember's first name
     * is "Yehudah" and his last name is "Glick", this method will return the String "Knesset Member
     * Yehudah Glick". @return the String representation of this KnessetMember.
     */
    String stringRepresentation(){
        return "Knesset Member "+knessetMemberFirstName+" "+knessetMemberLastName;
    }

    /**
     * Returns the interest value this KnessetMember assigns to the given Law.
     * @param law The law to assess.
     * @param surveyResult The result of a survey made for this law
     * @return the interest value this KnessetMember assigns to the given law. 0 if survey result does not
     * pass threshold.
     */
    double getLawScore(Law law, int surveyResult){
        double valueToLaw = law.lawSocialValue * socialTendency + law.lawEconomyValue * economyTendency
                + law.lawPoliticalValue * politicalTendency;
        if (surveyResult < knessetMemberSurveyThreshold){
            return noEnthusiasm;
        }
        else{
            return valueToLaw;
        }
    }

    /**
     * Returns true if this KnessetMember will join the given law (law score is bigger than
     * knessetMembersEnthusiasmThreshold), false otherwise.
     * @param law The law to asses.
     * @return true of this KnessetMember will join the given law, false otherwise.
     */
    boolean willJoinLaw(Law law, int surveyResult){
        double lawScore = getLawScore(law, surveyResult);
        if (lawScore > knessetMembersEnthusiasmThreshold){
            return true;
        }
        else{
            return false;
        }

    }

}
