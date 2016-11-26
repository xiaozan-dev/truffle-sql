package com.fivetran.truffle;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.fivetran.truffle.ParquetTestResources.documentPath;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class FileTest extends SqlTestBase {
    @Test
    public void primitive() throws IOException, SQLException {
        List<Object[]> rows = query("SELECT docId + 1 FROM TABLE(file('" + documentPath() + "'))");

        assertThat(rows, contains(new Object[][] {
                {11L},
                {21L}
        }));
    }

    @Test
    public void nestedSingleColumn() throws IOException, SQLException {
        List<Object[]> rows = query("SELECT docId, t.`name`.url FROM TABLE(file('" + documentPath() + "')) AS t");

        assertThat(rows, contains(new Object[][] {
                {10L, "http://A"},
                {10L, "http://B"},
                {10L, null},
                {20L, "http://C"}
        }));
    }

    @Test
    public void multipleRepeatedColumns() throws IOException, SQLException {
        List<Object[]> rows = query("SELECT docId, t.`name`.url, t.`name`.`language`.code FROM TABLE(file('" + documentPath() + "')) AS t");

        assertThat(rows, contains(new Object[][] {
                {10L, "http://A", "en-us"},
                {10L, "http://A", "en"},
                {10L, "http://B", null},
                {10L, null, "en-gb"},
                {20L, "http://C", null}
        }));
    }

    @Test
    public void nestedTwoStage() throws IOException, SQLException {
        List<Object[]> rows = query("SELECT docId, t.`name`.url " +
                                    "FROM (SELECT docId, `name` FROM TABLE(file('" + documentPath() + "'))) AS t");

        assertThat(rows, contains(new Object[][] {
                {10L, "http://A"},
                {10L, "http://B"},
                {10L, null},
                {20L, "http://C"}
        }));
    }
}
