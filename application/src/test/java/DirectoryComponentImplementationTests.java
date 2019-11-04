import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryComponentImplementationTests extends ImplementationTests {

    @Test
    public void testDirectoryComponentNotNull() {
        assertTrue("Default Directory is set up and not null", this.defaultDirectory != null);
        assertTrue("There exists a sub directory", this.defaultDirectory.hasSubDirectories);
        assertEquals("There are 3 files in the directory", 3, this.defaultDirectory.getNumFiles());
    }

}
