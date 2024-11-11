package store.global;

public enum Answer {

    IS_YES("Y"),
    IS_NO("N");

    private final String answer;

    Answer(String answer) {
        this.answer = answer;
    }

    public static Answer of(String ans) {
        if (ans.equals("Y")) {
            return IS_YES;
        }
        return IS_NO;
    }

    public static void validateYorN(String ans) {
        if (ans.equals(Answer.IS_YES.answer) || ans.equals(Answer.IS_NO.answer)) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT_ERR.getMessage());
    }
}
