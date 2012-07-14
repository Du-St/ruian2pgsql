/**
 * Copyright 2012 Miroslav Šulc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.fordfrog.ruian2pgsql;

import com.fordfrog.ruian2pgsql.convertors.MainConvertor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Objects;
import javax.xml.stream.XMLStreamException;

/**
 * Main class.
 *
 * @author fordfrog
 */
public class App {

    /**
     * Main method.
     *
     * @param args application arguments.
     *
     * @throws IOException        Thrown if I/O problem occurred.
     * @throws XMLStreamException Thrown if problem occurred while reading XML
     *                            stream.
     * @throws SQLException       Thrown if problem occurred while communicating
     *                            with database.
     */
    @SuppressWarnings("AssignmentToForLoopParameter")
    public static void main(final String[] args) throws IOException,
            XMLStreamException, SQLException {
        if (args.length == 0) {
            printUsage();

            return;
        }

        Path inputDirPath = null;
        String dbConnectionUrl = null;
        boolean createTables = false;
        Path logFilePath = Paths.get("log.txt");

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--create-tables":
                    createTables = true;
                    break;
                case "--db-connection-url":
                    i++;
                    dbConnectionUrl = args[i];
                    break;
                case "--input-dir":
                    i++;
                    inputDirPath = Paths.get(args[i]);
                    break;
                case "--log-file":
                    i++;
                    logFilePath = Paths.get(args[i]);
                    break;
            }
        }

        Objects.requireNonNull(dbConnectionUrl, "--db-connection-url must be "
                + "set (example: jdbc:postgresql://localhost/ruian?user=ruian"
                + "&password=p4ssw0rd)");
        Objects.requireNonNull(inputDirPath, "--input-dir must be set");

        MainConvertor.convert(
                inputDirPath, dbConnectionUrl, createTables, logFilePath);
    }

    /**
     * Prints application usage information.
     *
     * @throws UnsupportedEncodingException Thrown if UTF-8 encoding is not
     *                                      supported.
     * @throws IOException                  Thrown if I/O problem occurred.
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private static void printUsage() throws UnsupportedEncodingException,
            IOException {
        try (final InputStream inputStream =
                        App.class.getResourceAsStream("/usage.txt")) {
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        }
    }
}