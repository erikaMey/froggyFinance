package edu.utsa.cs3443.froggyfinance;

/**
 * Represents a dialog of the bugs, and toad
 *
 * @author erikamey
 * @since 10/30/25
 */
public class Dialog {
        private String open;
        private String right;
        private String wrong;

        /**
         * Constructs a new dialog
         *
         * @param open the dialog of the open scene
         * @param right the dialog  of right answer
         * @param wrong the dialog of wrong answer
         */
        public Dialog(String open, String right, String wrong){
            this.open = open;
            this.right = right;
            this.wrong = wrong;
        }

        /**
         * gets the dialog
         *
         * @return the dialog
         */
        public String getOpen(){
            return open;
        }

        /**
         * gets right dialog
         *
         * @return right dialog
         */
        public String getRight(){
            return right;
        }

        /**
         * gets wrong dialog
         *
         * @return wrong dialog
         */
        public String getWrong(){
            return wrong;
        }
    }
