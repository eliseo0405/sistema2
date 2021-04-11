 package backend.dao.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Elias Sirias
 */
public abstract class RandomTemplate
{
    private File fileHead;
    private File fileData;
    private CustomRandom customRandom;

    public RandomTemplate(File fileHead, File fileData)
    {
        this.fileHead = fileHead;
        this.fileData = fileData;
    }
    
    protected CustomRandom getCustomRandom() throws FileNotFoundException, IOException
    {
        if(customRandom == null)
        {
            customRandom = new CustomRandom(fileHead, fileData);
            return customRandom;
        }
        
        return customRandom;
    }
    
    protected void close() throws IOException
    {
        customRandom.close();
        customRandom = null;
    }
}
