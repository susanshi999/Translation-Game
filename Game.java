import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.StringBuffer;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A translation game which the player is suppose to translate sentences correctly 
 * from either french or english to the other.
 * 
 * @author Susan Shi
 * @version 1.0 2017-04-23
 * @version 1.1 2017-04-30
 * @version 1.5 2017-05-12
 * @version 2.0 2017-05-28
 * @version 2.1 2017-06-02
 */
public class Game
{
    //static fields
    private static final String ERROR_TITLE = "Error";
    private static final String HELP_TITLE = "Help";
    private static final String HINT_TITLE = "Hint";
    private static final String[] IMAGE_CITATION = {"https://www.pinterest.com/pin/344455071469728113/",
            "http://wide-wallpapers.net/strawberry-2-wide-wallpaper/",
            "http://www.dishmaps.com/pumpkin-pie/6805",
            "https://www.mslcomputers.co.uk/",
            "http://galleryhip.com/cartoon-teachers-teaching.html",
            "http://www.hendrypens.co.uk/special-edition-blue-sapphire-ballpoint-pen-88-33-p.asp",
            "http://www.health.com/health/gallery/0,,20408413,00.html#clean-pineapple-0",
            "https://www.wired.com/2016/10/review-misen-chefs-knife/",
            "http://www.aliquidationcentre.co.uk/Office-Furniture-Liverpool-Filing_Cabinets-Desks-Chairs.html",
            "https://www.ojcommerce.com/dainolite_ltd/p_29012_mt_sv/quartz_clock_plastic_face"};
    private static final String[] IMAGE_SOURCE = {"1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg",
            "6.jpg", "7.jpg", "8.jpg", "9.jpg","10.jpg"};          

    private static final String QUIT_TITLE = "Quitting";
    private static HashMap<Integer,SentencePair> sentence;
    // instance fields
    private  boolean correct = false;
    private String correctAnswer;
    private  int correctNumber = 0;
    private String currentSentence = null;
    private SentencePair currentSentencePair;
    private  String input;
    private  JLabel isCorrect;
    private  int key;
    private final int MAX_PLAYER = 20;
    private final int MAX_SENTENCES = 10;
    private String mark[] = new String[MAX_PLAYER];
    private String name[] = new String[MAX_PLAYER];
    private boolean notTested;
    private final int NUMBER_OF_LANGUAGES = 2;
    private int numberOfUsers;
    private  BufferedReader output;
    private PrintWriter outputFile;
    private  int playerGoal;
    private String previousScore;
    private Random randomGenerator = new Random();
    private final String OUTPUT_FILE = "user";
    private double score;
    private int sentenceCount = 0;
    private  int sentenceIndex;
    private int skipCount = 0;
    private File user;
    private String userName;
    // visual
    private JPanel buttonPanel;
    private JFrame frame;
    private final int FRAME_LENGTH = 400;
    private final int FRAME_WIDTH = 500;
    private JButton hintButton;
    private ImageComponent[] image;
    private JLabel imageCitation;
    private JPanel labelPanel;
    private final int PANEL_LENGTH = 100;
    private final int PANEL_WIDTH = 80;
    private JButton quitButton;
    private JLabel sentenceToDisplay;
    private JButton skipButton;
    private JTextField textField = new JTextField(20);
    /*static method*/
    /**
     * Launches the game.
     * 
     * @param argument not used
     */
    public static void main(String[] arguments)
    {
        Game game = new Game();
    } // end of main (String[] arguments)

    /* constructor */
    /**
     * Creates the basics of this game
     * Then waits for user to take action.
     */
    public Game()
    {

        // prepare sentence pairs 
        getInput();
        // get user's  goal
        setGoal();
        // prepare interfaces
        loadImageData();
        makeFrame();
        //display sentence and wait for user to do something
        displaySentence();

    } //end of constructor game()

    /* private methods*/
    /*
     * Randomly choose.a sentence that haven't been translated correctly to be the question.
     */
    private void chooseDisplayingSentence()
    {
        boolean sentenceFound = false;
        while (!sentenceFound)
        {
            //There are 2 languages but all we need is 0 and 1
            sentenceIndex = randomGenerator.nextInt(NUMBER_OF_LANGUAGES - 1);
            currentSentencePair =  sentence.get(key);
            if (!currentSentencePair.isBothCorrect())
            {
                if (currentSentencePair.isEnglishCorrect())
                {
                    correctAnswer = currentSentencePair.getFrench();
                    currentSentence = currentSentencePair.getEnglish();
                    sentenceFound = true;
                } // end of if (currentSentencePair.englishCorrect)
                else if (currentSentencePair.isFrenchCorrect())
                {
                    correctAnswer = currentSentencePair.getEnglish();
                    currentSentence = currentSentencePair.getFrench();
                    sentenceFound = true;
                } // end of if (currentSentencePair.frenchCorrect)
                else if (!currentSentencePair.isFrenchCorrect() && !currentSentencePair.isEnglishCorrect())
                {
                    //French = 0
                    //English = 1 
                    if (sentenceIndex == 1)
                    {

                        correctAnswer = currentSentencePair.getFrench();
                        currentSentence = currentSentencePair.getEnglish();
                        sentenceFound = true;
                    } 
                    else
                    {

                        correctAnswer = currentSentencePair.getEnglish();
                        currentSentence = currentSentencePair.getFrench();
                        sentenceFound = true;
                    } // end of if (sentenceIndex == 1)
                } // end of if (!currentSentencePair.frenchCorrect && !currentSentencePair.englishCorrect)
            }
            else
            { 
                key = randomGenerator.nextInt(MAX_SENTENCES) ;
            } // end of if (sentenceStillExist())
        } // end of while (!sentenceFound)
    } //  end of chooseDisplayingSentence()

    /*
     * Display a sentence with a corresponding image.
     */
    private void displaySentence() 
    {

        // quit condition
        if (correctNumber == playerGoal)
        {
            frame.setVisible(false);
            score = (double)correctNumber/(double)sentenceCount * 100;
            JOptionPane.showMessageDialog(null, 
                "Bye" + "\nYou have translated " + correctNumber +
                " out of " + sentenceCount +" sentences correctly." + "\nYou skipped " + skipCount +" sentences." +"\nYour score is " 
                + (int)score + "%",QUIT_TITLE, 
                JOptionPane.INFORMATION_MESSAGE);
            frame.setVisible(false);
            System.exit(0);

        } // end of if (correctNumber == playerGoal)
        else if (correctNumber < playerGoal)
        {
            // make sure when user enter input, we can go to isCorrect()
            notTested = true;
            // generate sentences
            key = randomGenerator.nextInt(MAX_SENTENCES) ;
            chooseDisplayingSentence();
            // display sentence with picture
            sentenceToDisplay.setText(currentSentence);
            imageCitation.setText(IMAGE_CITATION[key]);
            if (image[0] != null)
            {
                if (sentenceCount < 1) 
                {
                    frame.add(image[key],BorderLayout.CENTER);
                }
                else
                {
                    // Remove and replace the existing image. 
                    // Show sentenceToDisplay instead out isCorrect
                    labelPanel.add(sentenceToDisplay,BorderLayout.CENTER);
                    BorderLayout layout = (BorderLayout)(frame.getContentPane()).getLayout();
                    Component component = layout.getLayoutComponent(BorderLayout.CENTER);
                    frame.remove(component);
                    frame.add(image[key],BorderLayout.CENTER);
                    frame.pack();
                    frame.setVisible(true);
                    frame.repaint();

                } // end of (sentenceCount < 1) 
            } // end of if (image[0] != null)

        } // end of if (correctNumber < playerGoal)

    } // end of method displaySentence()

    /*
     * Load input data from file and place them in HashMap.
     */
    private void getInput() 
    {
        // get sentence pairs from file
        sentence = new HashMap<Integer,SentencePair>();
        final String TEXT_FILE = "input";
        try
        {
            // Establish a connection to the text file.
            BufferedReader inputFile = new BufferedReader(new FileReader(TEXT_FILE));

            //Read from the text file and echo to the console.
            String lineOfText = inputFile.readLine();
            int count = 0;

            while (lineOfText != null)
            {
                String[] parts = lineOfText.split("/");
                String english = parts[0];
                String french = parts[1];

                sentence.put(count, new SentencePair(english,
                        french));
                count++;
                lineOfText = inputFile.readLine();
            } // while(lineOfText != null)

            // Wrap up.
            inputFile.close();
        }
        catch (IOException exception)
        {

        }
    } // end of getInput()

    /*
     * Load image data for later use.
     */    
    private void loadImageData()
    {
        image = new ImageComponent[MAX_SENTENCES];

        for (int imageNumber = 0; imageNumber < MAX_SENTENCES; imageNumber++)
        {
            image[imageNumber] = new ImageComponent(IMAGE_SOURCE[imageNumber]);
        } // end of  for (int imageNumber = 0; imageNumber < NUMBER_OF_IMAGES; imageNumber++)
    } // end of method loadImageData()

    /*
     * Creates the control-buttons panel.
     */
    private void makeButtonPanel()
    {
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLUE);
        buttonPanel.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_LENGTH));

        quitButton = new JButton("Quit");
        skipButton = new JButton("Skip");
        hintButton = new JButton("Hint");

        ObjectListener actionListener = new ObjectListener();

        buttonPanel.add(hintButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(skipButton);

        quitButton.addActionListener(actionListener);
        skipButton.addActionListener(actionListener);
        hintButton.addActionListener(actionListener);

    } // end of method makeButtonPanel()

    /*
     * Creates the application frame and its content.
     */
    private void makeFrame()
    {
        frame = new JFrame("Translation Game");
        frame.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_LENGTH));
        frame.getContentPane().setBackground(Color.WHITE);

        makeButtonPanel();
        makeLabelPanel();
        JPanel notUsedPanel =  new JPanel();
        notUsedPanel.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_LENGTH));
        notUsedPanel.setBackground(Color.BLUE);

        ObjectListener actionListener = new ObjectListener();
        textField.addActionListener(actionListener);

        // text field
        JPanel panel = new JPanel();
        JLabel translationLabel = new JLabel("Translation: ");
        panel.add(translationLabel);
        panel.add(textField);
        panel.setBackground(Color.WHITE);
        frame.add(panel,BorderLayout.PAGE_END);

        frame.add(buttonPanel, BorderLayout.LINE_END);
        frame.add(notUsedPanel,BorderLayout.LINE_START);
        frame.add(labelPanel,BorderLayout.PAGE_START);

        frame.pack();
        frame.setVisible(true);
    } // end of method makeFrame()

    /*
     * Creates a panel for image citation and sentence to be displayed.
     */
    private void makeLabelPanel()
    {
        labelPanel = new JPanel();
        labelPanel.setBackground(Color.CYAN);
        imageCitation = new JLabel("",JLabel.CENTER);
        sentenceToDisplay = new JLabel("",JLabel.CENTER);
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.add(imageCitation);
        labelPanel.add(sentenceToDisplay);
    } // end of method makeLabelPanel()

    /*
     * Check user's translation.
     * Show the result.
     */
    private void isCorrect()
    {
        sentenceCount ++;  
        if(input.equals(correctAnswer))
        {
            correctNumber ++;

            if (currentSentence.equals(currentSentencePair.getFrench()))
            {
                currentSentencePair.setEnglishCorrect(true);
            } 
            else
            {
                currentSentencePair.setFrenchCorrect(true);
            } // end of if (currentSentence.equals(currentSentencePair.getFrench()))

            if(currentSentencePair.isEnglishCorrect() && currentSentencePair.isFrenchCorrect())
            {
                currentSentencePair.setBothCorrect(true);
            } // end of if(currentSentencePair.isEnglishCorrect() && currentSentencePair.isFrenchCorrect())
            JOptionPane.showMessageDialog(null, "Correct",
            HELP_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } 
        else
        {
            JOptionPane.showMessageDialog(null, "Wrong",
            HELP_TITLE, JOptionPane.INFORMATION_MESSAGE);
        } // end of if(input.equals(correctAnswer))

        // display result 

        
        textField.setText("");

        frame.pack();
        frame.setVisible(true);
        frame.repaint();
        notTested = false;

    } //end of method isCorrect()

    /*
     * Introduce this game to user.
     * Ask the user for goal.
     * Check if user's goal is valid.
     * Set goal for user.
     */
    private void setGoal()
    {
        boolean goalSet = false;
        //  instruct user and get goal
        JOptionPane.showMessageDialog(null, "This is a translation game.",
            HELP_TITLE, JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(null, "Click on \"Quit\" to exit", 
            HELP_TITLE, 
            JOptionPane.INFORMATION_MESSAGE);

        while (!goalSet)
        {
            String inputInterger = JOptionPane.showInputDialog("Your goal: ");
            try
            {
                playerGoal = Integer.parseInt(inputInterger);
                if(playerGoal >= 0 && playerGoal<= MAX_SENTENCES)
                {
                    goalSet = true;
                } 
                else
                {
                    JOptionPane.showMessageDialog(null, 
                        inputInterger + " is not a valid integer in between the range 1-10. please try again", ERROR_TITLE, 
                        JOptionPane.ERROR_MESSAGE);
                    goalSet = false;
                } // end of if(playerGoal >= 0 && playerGoal<= MAX_SENTENCES)
            }
            catch (NumberFormatException e)
            {

                JOptionPane.showMessageDialog(null, 
                    inputInterger + " is not a valid integer. please try again", ERROR_TITLE, 
                    JOptionPane.ERROR_MESSAGE);
                goalSet = false;
            }
        } // end of while(!goalSet)
    } // end of method setGoal()


    /*private classes*/
    private class ImageComponent extends Component
    {
        // class fields
        private static final int NO_PROBLEMS_ENCOUNTERED = 0;
        private static final int PROBLEMS_ENCOUNTERED = -1;

        // instance fields
        private BufferedImage bufferedImage;
        private int status;

        /* constructors */

        /*
         * Creates a component with a drawn image. If the image was
         * drawn, the component's status is NO_PROBLEMS_ENCOUNTERED;
         * otherwise, PROBLEMS_ENCOUNTERED.
         */
        public ImageComponent(String fileName)
        {
            bufferedImage = null;
            status = NO_PROBLEMS_ENCOUNTERED;
            try
            {
                bufferedImage = ImageIO.read(new File(fileName));
            }
            catch (IOException exception)
            {
                status = PROBLEMS_ENCOUNTERED;
            } // end of catch (IOException exception)
        } // end of constructor ImageComponent(String fileName)

        /* accessors */

        /*
         * Returns the status of this component: NO_PROBLEMS_ENCOUNTERED
         * or PROBLEMS_ENCOUNTERED.
         */
        public int getStatus()
        {
            return status;
        } // end of method getStatus()

        /* mutators */

        /*
         * Called when the contents of the component should be painted, such as
         * when the component is first being shown or is damaged and in need of
         * repair.
         */
        public void paint(Graphics graphicsContext)
        {
            graphicsContext.drawImage(bufferedImage, 70, 50, null);
        } // end of method paint(Graphics graphicsContext)

    } // end of class ImageComponent

    /*
     * A listener which can be registered by an event source and which
     * can receive event objects.
     */
    private class ObjectListener implements ActionListener
    {
        /*
         * Responds to control-button events and textfield events.
         */
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();

            if (source == quitButton)
            {
                frame.setVisible(false);
                if (sentenceCount > 0)
                {

                    score = (double)correctNumber/(double)sentenceCount * 100;
                    JOptionPane.showMessageDialog(null, 
                        "Bye" + "\nYou have translated " + correctNumber +
                        " out of " + sentenceCount +" sentences correctly." + "\nYou skipped " + skipCount +" sentences." + "\nYour score is " + (int)score + "%",QUIT_TITLE, 
                        JOptionPane.INFORMATION_MESSAGE);

                    frame.setVisible(false);
                    System.exit(0);
                } 
                else
                {
                    // if sentence Count = 0, player didn't play the game, can't caluculate result or record score
                    JOptionPane.showMessageDialog(null, 
                        "Bye" + "\nYou didn't play this game 0_0 ",QUIT_TITLE, 
                        JOptionPane.INFORMATION_MESSAGE);
                } // end of if (sentenceCount > 0)
            } // end of if(source == quitButton)
            else if (source == skipButton)
            {
                // skip the current question
                JLabel empty = new JLabel("");
                skipCount++;
                BorderLayout layout = (BorderLayout)(frame.getContentPane()).getLayout();
                Component componentw = layout.getLayoutComponent(BorderLayout.CENTER);
                frame.remove(componentw);
                frame.add(empty,BorderLayout.CENTER);
                textField.setText("");

                frame.pack();
                frame.setVisible(true);
                frame.repaint();
                displaySentence();
            } // end of if (source == OKButton)
            else if (source == hintButton)
            {
                JOptionPane.showMessageDialog(null, 
                    "When entering French translations, remember to add \"le, la, or l\"." +
                    "\nDon't add \"the\" or \"a\" to English words." +
                    "\nIf you don't know the correct answer to a question, you can always click \"skip\"." +
                    "\nYou can always stop the game by clicking \"quit\".",HINT_TITLE, 
                    JOptionPane.INFORMATION_MESSAGE);

            } // end of if (source == hintButton)
            else if (source == textField)
            {
                String text = textField.getText();
                input = text.trim();
                // use entre key to perform different actions
                
                    isCorrect();
                
                    displaySentence();
            } // end of if (source == textField)
        } // end of method actionPerformed (ActionEvent event)
    } // end of class ObjectListener
} // end of class Game
