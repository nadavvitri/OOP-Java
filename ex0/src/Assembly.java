/**
 * Created by nadav vitri on 12/03/2017.
 */
public class Assembly {
    static int notFoundNumber = -1;
    static int knessetMemberSize = 120;
    int maxLawCapacity, maxSupportedLawsPerKnessetMember;
    Law[] lawsInAssembly;
    int[]  lawSurveyValue, lawsPerKnessetMember;
    KnessetMember[] knessetMembersInAssembly;

    /**
     * Creates a new assembly with the given parameters.
     * @param maxLawCapacity The maximal number of laws this assembly can hold.
     * @param maxSupportedLawsPerKnessetMember The maximal number of laws this assembly allows a single
     * KnessetMember to support at the same time.
     */
    Assembly(int maxLawCapacity, int maxSupportedLawsPerKnessetMember){
        this.maxLawCapacity = maxLawCapacity;
        this.maxSupportedLawsPerKnessetMember = maxSupportedLawsPerKnessetMember;
        this.lawsInAssembly = new Law[maxLawCapacity];
        this.lawSurveyValue = new int[maxLawCapacity];
        this.knessetMembersInAssembly = new KnessetMember[knessetMemberSize];
        this.lawsPerKnessetMember = new int[knessetMemberSize];
    }

    /**
     * Adds the given law to this assembly, if there is place available, and it isn't already in the assembly.
     * @param law The law to add to this assembly.
     * @param surveyResult The survey result of the law.
     * @return a non-negative id number for the law if there was a spot and the law was successfully
     * added, or if the law was already in the assembly; a negative number otherwise.
     */
    int addLawToAssembly(Law law, int surveyResult){
        for (int i = 0; i < lawsInAssembly.length; i++){
            if (lawsInAssembly[i] == law){
                return notFoundNumber;
            }
            if (lawsInAssembly[i] == null){
                lawsInAssembly[i] = law;
                lawSurveyValue[i] = surveyResult;
                return i;
            }
        }
        return notFoundNumber;
    }

    /**
     * updates the survey result of lawId with a new value
     * @param law law to be updated
     * @param newSurveyValue new survey value.
     */
    void updateSurveyResultOfLaw(Law law, int newSurveyValue){
        for (int i = 0; i < lawsInAssembly.length; i++){
            if (lawsInAssembly[i] == law){
                lawSurveyValue[i] = newSurveyValue;
            }
        }
    }

    /**
     * Returns true if the given number is an id of some law in the assembly, false otherwise.
     * @param lawId The id to check.
     * @return true if the given number is an id of some law in the assembly, false otherwise.
     */
    boolean isLawIDValid(int lawId){
        if (lawId >= lawsInAssembly.length || lawsInAssembly[lawId] == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Returns the non-negative id number of the given law if he is discussed by this assembly, -1 otherwise.
     * @param law The law for which to find the id number.
     * @return a non-negative id number of the given law if he is discussed by this assembly, -1 otherwise.
     */
    int getLawId(Law law) {
        for (int i = 0; i < lawsInAssembly.length; i++) {
            if (lawsInAssembly[i] == law) {
                return i;
            }
        }
        return notFoundNumber;
    }

    /**
     * Registers the given KnessetMember to this assembly, if there is a spot available.
     * @param KnessetMember The KnessetMember to register to this assembly.
     * @return a non-negative id number for the KnessetMember if there was a spot and the patron was
     * successfully registered, a negative number otherwise.
     */
    int registerKnessetMember(KnessetMember KnessetMember){
        for (int i = 0; i < knessetMembersInAssembly.length; i++){
            if (knessetMembersInAssembly[i] == null){
                knessetMembersInAssembly[i] = KnessetMember;
                return i;
            }
        }
        return notFoundNumber;
    }

    /**
     * Returns true if the given number is an id of a KnessetMember in the assembly, false otherwise.
     * @param KnessetMemberId The id to check.
     * @return  true if the given number is an id of a KnessetMember in the assembly, false otherwise.
     */
    boolean isKnessetMemberIdValid(int KnessetMemberId){
        if (knessetMembersInAssembly[KnessetMemberId] == null ||
                KnessetMemberId >= knessetMembersInAssembly.length){
            return false;
        }
        return true;
    }

    /**
     * Returns the non-negative id number of the given KnessetMember if he is registered to this assembly,
     * -1 otherwise.
     * @param KnessetMember The KnessetMember for which to find the id number.
     * @return a non-negative id number of the given KnessetMember if he is registered to this assembly,
     * -1 otherwise.
     */
    int getKnessetMemberId(KnessetMember KnessetMember){
        for (int i = 0; i < knessetMembersInAssembly.length; i++){
            if (knessetMembersInAssembly[i] == KnessetMember)
                return i;
        }
        return notFoundNumber;
    }

    /**
     * adds KnessetMember to law, if the KnessetMember will support the law according to the survey results.
     * @param lawId The id number of the law to borrow.
     * @param KnessetMemberId The id number of the KnessetMember that will support the law.
     * @param surveyResult The survey result of the law to support.
     * @return true if the KnessetMember was added successfully, false otherwise.
     */
    boolean supportLaw(int lawId, int KnessetMemberId, int surveyResult){
        KnessetMember member = this.knessetMembersInAssembly[KnessetMemberId];
        Law law = this.lawsInAssembly[lawId];
        if (member.willJoinLaw(law, surveyResult) && this.lawsPerKnessetMember[KnessetMemberId]
                < this.maxSupportedLawsPerKnessetMember){
            law.addJoinedKnessetMember();
            this.lawsPerKnessetMember[KnessetMemberId]++;
            return true;
        }
        return false;
    }

    /**
     * Suggest to the KnessetMember with the given id a law which suits him the best (maximum score of the
     * laws in the assembly).
     * @param KnessetMemberId The id number of the KnessetMember to suggest the law to.
     * @return The best law to match the KnessetMember preferences. Null if there aren't any (case all laws
     * get a zero score) available.
     */
    Law suggestLawToKnessetMember(int KnessetMemberId){
        double currentBestLawScore = 0;
        Law bestLaw = null;
        KnessetMember member = this.knessetMembersInAssembly[KnessetMemberId];
        for (int i = 0 ; i < this.lawsInAssembly.length; i++) {
            double lawScore = member.getLawScore(this.lawsInAssembly[i], this.lawSurveyValue[i]);
            if (lawScore > currentBestLawScore){
                currentBestLawScore = lawScore;
                bestLaw = this.lawsInAssembly[i];
            }
        }
        return bestLaw;

    }

}
