/* Generated By:JavaCC: Do not edit this line. NeticaReaderConstants.java */
package edu.ucla.belief.io.netica;

public interface NeticaReaderConstants {

  int EOF = 0;
  int COMMENT = 5;
  int UNDEF = 6;
  int DEFINE = 7;
  int ID = 8;
  int NUM = 9;
  int STRING = 10;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<COMMENT>",
    "\"@undef\"",
    "\"define\"",
    "<ID>",
    "<NUM>",
    "<STRING>",
    "\";\"",
    "\"{\"",
    "\"}\"",
    "\"=\"",
    "\"(\"",
    "\",\"",
    "\")\"",
  };

}
