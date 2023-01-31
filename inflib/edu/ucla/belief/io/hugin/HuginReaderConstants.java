/* Generated By:JavaCC: Do not edit this line. HuginReaderConstants.java */
package edu.ucla.belief.io.hugin;

public interface HuginReaderConstants {

  int EOF = 0;
  int COMMENT = 5;
  int CONTINUOUS = 6;
  int DISCRETE = 7;
  int NODE = 8;
  int DECISION = 9;
  int UTILITY = 10;
  int POTENTIAL = 11;
  int NET = 12;
  int CLASS = 13;
  int MAP_LIT = 14;
  int NUM = 15;
  int VALSTRING = 16;
  int ID = 17;
  int STRING = 18;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<COMMENT>",
    "\"continuous\"",
    "\"discrete\"",
    "\"node\"",
    "\"decision\"",
    "\"utility\"",
    "\"potential\"",
    "\"net\"",
    "\"class\"",
    "\"__Map__\"",
    "<NUM>",
    "<VALSTRING>",
    "<ID>",
    "<STRING>",
    "\"{\"",
    "\"}\"",
    "\"=\"",
    "\";\"",
    "\"(\"",
    "\"|\"",
    "\")\"",
    "\",\"",
  };

}
