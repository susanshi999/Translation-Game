/**
 *  A pair of sentences composed of its french and english translation.
 *  
 * 
 * @author Susan Shi 
 * @version 1.0 2017-04-23
 * @version 1.1 2017-06-02
 */

public class SentencePair
{
    // instance fields
    private boolean bothCorrect;
    private boolean englishCorrect;
    private boolean frenchCorrect;
    private String english;
    private String french;
    /* constructors */

    /**
     * Creates a SentencePair with french and english translation.
     */
    public SentencePair(String english, String french)
    {
        this.english =english;
        this.french =french;
    }  // end of constructor SentencePair(String english, String french)

    /* accessors */

    /**
     * Returns the English translation of this SentencePair.
     * @return the English part
     */
    public String getEnglish()
    {
        return english;
    } // end of method getEnglish()

    /**
     * Returns the French translation of this SentencePair.
     * @return the French part
     */
    public String getFrench()
    {
        return french;
    } // end of method getEnglish()

    /**
     * Returns if user had correctly translated the french part of this SentencePair.
     * @return englishCorrect
     */
    public boolean isEnglishCorrect()
    {
        return englishCorrect;
    } // end of method isEnglishCorrect()

    /**
     * Returns if user had correctly translated the english part of this SentencePair.
     * @return frenchCorrect
     */
    public boolean isFrenchCorrect()
    {
        return frenchCorrect;
    } // end of method isFrenchCorrect()

    /**
     * Returns if user had correctly translated both parts of this SentencePair correctly.
     * @return bothCorrect
     */
    public boolean isBothCorrect()
    {
        return bothCorrect;
    } // end of method isBothCorrect()

    /*mutators*/
    /**
     * Sets if the French part of this SentencePair has been translated correctly
     * @param correct- if the English translation given for this SentencePair was correct
     */
    public void setEnglishCorrect(boolean correct)
    {
        englishCorrect = correct;
    } // end of method setEnglishCorrect(boolean correct)

    /**
     * Sets if the English part of this SentencePair has been translated correctly
     * @param correct- if the French translation given for this SentencePair was correct
     */
    public void setFrenchCorrect(boolean correct)
    {
        frenchCorrect = correct;
    } // end of method setFrenchCorrect(boolean correct)

    /**
     * Sets if both parts of this SentencePair have been translated correctly
     * @param correct- if both the English translation and the French translation given for this SentencePair were correct
     */
    public void setBothCorrect(boolean correct)
    {
        bothCorrect = correct;
    } // end of method setBothCorrect(boolean correct)
} // end of class SentencePair

   