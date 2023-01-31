/* Generated By:JavaCC: Do not edit this line. HuginReader.java */
package edu.ucla.belief.io.hugin;

import edu.ucla.belief.io.dsl.DSLConstants;
import edu.ucla.belief.*;
import edu.ucla.belief.io.*;
//import edu.ucla.util.JVMTI;
import edu.ucla.util.ProgressMonitorable;

import java.util.*;
import java.io.*;

/** @author James Park
    @author keith cascio
    @since 20020226
    @since 20060518 */
public class HuginReader implements ProgressMonitorable, HuginReaderConstants {
        public static final String STR_MAP_TOKEN = "__Map__";

        /** @since 20060804 */
        public void setRelaxed( boolean flag ){
                this.myFlagRelaxed = flag;
        }

        public void setEstimator( SkimmerEstimator huginworkestimator ){
                myEstimator = huginworkestimator;
                if( myEstimator != null ){
                        myProgressMax = myEstimator.getProgressMax();
                }
        }

        public void setConstructionTask( NodeLinearTask task ){
                myConstructionTask = task;
        }

        public BeliefNetwork beliefNetwork() throws ParseException {
                BeliefNetwork ret = null;
                try{
                        ret = HuginReader.this.doBeliefNetwork();
                }finally{
                        myFlagFinished = true;
                }
                return ret;
        }

        /** interface ProgressMonitorable
		@since 20060518 */
        public int getProgress(){
                return myProgress;
        }

        /** interface ProgressMonitorable
		@since 20060518 */
        public int getProgressMax(){
                return myProgressMax;
        }

        /** interface ProgressMonitorable
		@since 20060519 */
        public boolean isFinished(){
                return myFlagFinished;
        }

        /** interface ProgressMonitorable
		@since 20060519 */
        public String getNote(){
                return "reading file...";
        }

        /** interface ProgressMonitorable
		@since 20060519 */
        public ProgressMonitorable[] decompose(){
                if( myDecomp == null ) myDecomp = new ProgressMonitorable[] { HuginReader.this };
                return myDecomp;
        }

        /** interface ProgressMonitorable
		@since 20060519 */
        public String getDescription(){
                return "HuginReader";
        }

    public static void main(String args[])
    {
                try
                {
                        Definitions.STREAM_TEST.println("Starting");
                        for(int i=0;i<args.length;i++)
                        {
                        Definitions.STREAM_TEST.println(args[i]);
                                HuginReader reader=new HuginReader(new java.io.FileReader(new File(args[0])));
                                //HuginNet obj=reader.huginNet();
                                //obj.makeNetwork();
                                Definitions.STREAM_TEST.println("success");
                        }
                } catch(Exception e)
                {
                        System.err.println(e);
                        e.printStackTrace();
                }
        }

        //private long elapsedNodeBlock      = 0;
        //private long elapsedPotentialBlock = 0;

        private SkimmerEstimator myEstimator;
        private int     myProgress     = 0;
        private int     myProgressMax  = 1;
        private boolean myFlagFinished = false;
        private ProgressMonitorable[] myDecomp;
        private NodeLinearTask myConstructionTask;
        private boolean myFlagRelaxed = false;

  final public BeliefNetwork doBeliefNetwork() throws ParseException {
        //long start = JVMTI.getCurrentThreadCpuTimeUnsafe();

        //HuginNet net=new HuginNetImpl();
        HuginNode node = null;
        HuginPotential potential = null;
        Map nodeNamesToFiniteVariables = new HashMap();
        Map finiteVariablesToPotentials = new HashMap();
        Collection potentials = new LinkedList();
        Map netParams = null;
        Variable fVar = null;
        String tempName = null;
        HuginFileVersion hfv = HuginFileVersion.V57;

        int incNodeBlock = 0;
        int incPoteBlock = 0;
        if( myEstimator != null ){
                myProgressMax = myEstimator.getProgressMax();
                incNodeBlock  = myEstimator.getProgressIncrementNodeBlock();
                incPoteBlock  = myEstimator.getProgressIncrementPoteBlock();
        }
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONTINUOUS:
      case DISCRETE:
      case NODE:
      case DECISION:
      case UTILITY:
      case POTENTIAL:
      case NET:
      case CLASS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONTINUOUS:
      case DISCRETE:
      case NODE:
      case DECISION:
      case UTILITY:
        node = nodeBlock();
                        if( Thread.currentThread().isInterrupted() ) {if (true) return (BeliefNetwork)null;}
                        //net.add(node);
                        //fVar = node.makeVariable();
                        //fVar.userobject = node;
                        nodeNamesToFiniteVariables.put( node.getID(), node );
                        //net.add( node );
                        myProgress += incNodeBlock;
        break;
      case POTENTIAL:
        potential = potentialBlock();
                        if( Thread.currentThread().isInterrupted() ) {if (true) return (BeliefNetwork)null;}
                        //net.add(potential);
                        potentials.add( potential );
                        myProgress += incPoteBlock;
        break;
      case NET:
        netParams = netBlock();
                        hfv = HuginFileVersion.V57;
                        //net.setParams(netParams);
                        HuginReader.this.digest( netParams );
        break;
      case CLASS:
        netParams = classBlock(nodeNamesToFiniteVariables, potentials);
                        hfv = HuginFileVersion.V61;
                        HuginReader.this.digest( netParams );
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
                //long mid0 = JVMTI.getCurrentThreadCpuTimeUnsafe();

                for( Iterator it = potentials.iterator(); it.hasNext(); )
                {
                        potential = (HuginPotential) it.next();
                        tempName = potential.joints.get(0).toString();
                        fVar = (Variable)nodeNamesToFiniteVariables.get( tempName );
                        finiteVariablesToPotentials.put( fVar, new TableShell( (Table) potential.makePotential(nodeNamesToFiniteVariables) ) );
                }

                //long mid1 = JVMTI.getCurrentThreadCpuTimeUnsafe();

                HuginNetImpl ret = new HuginNetImpl( false );
                ret.induceGraph( finiteVariablesToPotentials, myConstructionTask );
                if( Thread.currentThread().isInterrupted() ) {if (true) return (BeliefNetwork)null;}
                ret.setVersion( hfv );
                if( netParams != null ) ret.setParams( netParams );

                //long end = JVMTI.getCurrentThreadCpuTimeUnsafe();

                //long first = mid0 - start;
                //long second = mid1 - mid0;
                //long last  = end - mid1;
                //double total = (double) (end - start);

                //double estimFrac = ((double)myEstimator.getCost()) / total;
                //double firstFrac = ((double)first) / total;
                //double nodeFrac  = ((double)elapsedNodeBlock) / total;
                //double poteFrac  = ((double)elapsedPotentialBlock) / total;
                //double secondFrac = ((double)second) / total;
                //double lastFrac = ((double)last) / total;

                //System.out.println( "HuginReader.beliefNetwork()" );
                //System.out.println( "    estimation      : " + NetworkIO.FORMAT_PROFILE_PERCENT.format(estimFrac) + " (" + NetworkIO.formatTime(myEstimator.getCost())
                //              + "),\n    parsing         : " + NetworkIO.FORMAT_PROFILE_PERCENT.format(firstFrac) + " (" + NetworkIO.formatTime(first)
                //              + "),\n        nodes       : " + NetworkIO.FORMAT_PROFILE_PERCENT.format(nodeFrac) + " (" + NetworkIO.formatTime(elapsedNodeBlock)
                //              + "),\n        potentials  : " + NetworkIO.FORMAT_PROFILE_PERCENT.format(poteFrac) + " (" + NetworkIO.formatTime(elapsedPotentialBlock)
                //              + "),\n    make potentials : " + NetworkIO.FORMAT_PROFILE_PERCENT.format(secondFrac) + " (" + NetworkIO.formatTime(second)
                //              + "),\n    new HuginNetImpl: " + NetworkIO.FORMAT_PROFILE_PERCENT.format(lastFrac) + " (" + NetworkIO.formatTime(last) + ")" );

                myFlagFinished = true;
                {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public Map netBlock() throws ParseException {
        Map values;
    jj_consume_token(NET);
    jj_consume_token(19);
    values = valueList();
    jj_consume_token(20);
                {if (true) return values;}
    throw new Error("Missing return statement in function");
  }

  final public Map classBlock(Map nodeNamesToFiniteVariables, Collection potentials) throws ParseException {
        HuginNode node = null;
        HuginPotential potential = null;
        Map result = new HashMap();
        Token key;
        Object value;
        LinkedList mapPrefixes = new LinkedList();
    jj_consume_token(CLASS);
    jj_consume_token(ID);
    jj_consume_token(19);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONTINUOUS:
      case DISCRETE:
      case NODE:
      case DECISION:
      case UTILITY:
      case POTENTIAL:
      case ID:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONTINUOUS:
      case DISCRETE:
      case NODE:
      case DECISION:
      case UTILITY:
        node = nodeBlock();
                        nodeNamesToFiniteVariables.put( node.getID(), node );
        break;
      case POTENTIAL:
        potential = potentialBlock();
                        potentials.add( potential );
        break;
      case ID:
        key = jj_consume_token(ID);
        jj_consume_token(21);
        value = value(key.image.startsWith( PropertySuperintendent.KEY_HUGIN_POSITION ) || (key.image.startsWith( DSLConstants.STR_KEY_PREFIX ) && !key.image.startsWith( DSLConstants.KEY_OBSERVATION_COST_NODECOSTSLIST ) ));
        jj_consume_token(22);
                        if( value == STR_MAP_TOKEN )//it's a Map
                        {
                                Map newMap = new HashMap();
                                mapPrefixes.add( key.image );
                                result.put( key.image, newMap );
                        }
                        else
                        {
                                Map destination = result;
                                String destinationKey = key.image;

                                if( !mapPrefixes.isEmpty() )
                                {
                                        for( Iterator it= mapPrefixes.iterator(); it.hasNext(); )
                                        {
                                                String currentPrefix = (String) it.next();
                                                if( key.image.startsWith( currentPrefix ) )
                                                {
                                                        destination = (Map) result.get( currentPrefix );
                                                        destinationKey = key.image.substring( currentPrefix.length() );
                                                }
                                        }
                                }

                                if( PropertySuperintendent.KEY_HUGINITY.equals( destinationKey ) && PropertySuperintendent.VALUE_PERMISSIVE.equals( value ) ){
                                        HuginReader.this.setRelaxed( true );
                                }

                                destination.put( destinationKey,value );
                        }
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }

    jj_consume_token(20);
                {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public HuginPotential potentialBlock() throws ParseException {
        //long start = JVMTI.getCurrentThreadCpuTimeUnsafe();
        Token var;
        List joints=new ArrayList();
        List conditioned=new ArrayList();
        Map values;
    jj_consume_token(POTENTIAL);
    jj_consume_token(23);
    joints = idList();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 24:
      jj_consume_token(24);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        conditioned = idList();
        break;
      default:
        jj_la1[4] = jj_gen;
        ;
      }
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    jj_consume_token(25);
    jj_consume_token(19);
    values = valueList();
    jj_consume_token(20);
                HuginPotential ret = new HuginPotential( joints, conditioned, values, myFlagRelaxed );
                //long end = JVMTI.getCurrentThreadCpuTimeUnsafe();
                //elapsedPotentialBlock += (end - start);
                {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public List idList() throws ParseException {
        Token t;
        List result=new ArrayList();
    label_3:
    while (true) {
      t = jj_consume_token(ID);
                result.add(t.image);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_3;
      }
    }
                {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public HuginNode nodeBlock() throws ParseException {
        //long start = JVMTI.getCurrentThreadCpuTimeUnsafe();
        Token vartype=null;
        Token nodetype=null;
        Token name;
        Map values;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CONTINUOUS:
    case DISCRETE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONTINUOUS:
        vartype = jj_consume_token(CONTINUOUS);
        break;
      case DISCRETE:
        vartype = jj_consume_token(DISCRETE);
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NODE:
      nodetype = jj_consume_token(NODE);
      break;
    case DECISION:
      nodetype = jj_consume_token(DECISION);
      break;
    case UTILITY:
      nodetype = jj_consume_token(UTILITY);
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    name = jj_consume_token(ID);
    jj_consume_token(19);
    values = valueList();
    jj_consume_token(20);
                int vt;
                if(vartype==null){
                        vt=DISCRETE;
                }else{
                        vt=vartype.kind;
                }
                HuginNode ret = new HuginNodeImpl( name.image, values, nodetype.kind, vt );
                //long end = JVMTI.getCurrentThreadCpuTimeUnsafe();

                //elapsedNodeBlock += (end - start);
                {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public Map valueList() throws ParseException {
        Map result=new HashMap();
        Token key;
        Object value;
        LinkedList mapPrefixes = new LinkedList();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_4;
      }
      key = jj_consume_token(ID);
      jj_consume_token(21);
      value = value(key.image.startsWith( PropertySuperintendent.KEY_HUGIN_POSITION ) || (key.image.startsWith( DSLConstants.STR_KEY_PREFIX ) && !key.image.startsWith( DSLConstants.KEY_OBSERVATION_COST_NODECOSTSLIST ) ));
      jj_consume_token(22);
                if( value == STR_MAP_TOKEN )//it's a Map
                {
                        Map newMap = new HashMap();
                        mapPrefixes.add( key.image );
                        result.put( key.image, newMap );
                }
                else
                {
                        Map destination = result;
                        String destinationKey = key.image;

                        if( !mapPrefixes.isEmpty() )
                        {
                                for( Iterator it= mapPrefixes.iterator();
                                        it.hasNext(); )
                                {
                                        String currentPrefix = (String) it.next();
                                        if( key.image.startsWith( currentPrefix ) )
                                        {
                                                destination = (Map) result.get( currentPrefix );
                                                destinationKey = key.image.substring( currentPrefix.length() );
                                        }
                                }
                        }

                        destination.put( destinationKey,value );
                }
    }
                {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public Object value(boolean asInteger) throws ParseException {
  Token x;
  List newList;
  Object val;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MAP_LIT:
      x = jj_consume_token(MAP_LIT);
                    {if (true) return STR_MAP_TOKEN;}/* let valueList() know about a new Map */
      break;
    case VALSTRING:
      x = jj_consume_token(VALSTRING);
                    {if (true) return stripQuotes( x.image );}
      break;
    case NUM:
      x = jj_consume_token(NUM);
                    {if (true) return asInteger ? ((Number) new Integer( x.image )) : ((Number) new Double( x.image ));}
      break;
    default:
      jj_la1[12] = jj_gen;
      if (jj_2_1(2)) {
        x = jj_consume_token(ID);
                    {if (true) return x.image;}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ID:
          val = function();
                    {if (true) return val;}
          break;
        case 23:
          jj_consume_token(23);
           newList=new ArrayList();
          label_5:
          while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MAP_LIT:
            case NUM:
            case VALSTRING:
            case ID:
            case 23:
              ;
              break;
            default:
              jj_la1[11] = jj_gen;
              break label_5;
            }
            val = value(asInteger);
                                                                 newList.add(val);
          }
          jj_consume_token(25);
                    {if (true) return newList;}
          break;
        default:
          jj_la1[13] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  String stripQuotes(String str) throws ParseException {
        return str.substring(1,str.length()-1);
  }

  private void digest(Map netParams) throws ParseException {
        try{
                if( netParams == null ) return;
                if( netParams.containsKey( PropertySuperintendent.KEY_HUGINITY ) && (PropertySuperintendent.VALUE_PERMISSIVE.equals( netParams.get( PropertySuperintendent.KEY_HUGINITY ) ) ) ) HuginReader.this.setRelaxed( true );
        }catch( Exception exception ){
                System.err.println( "warning! HuginReader.digest() caught " + exception );
        }
  }

  final public HuginFunction function() throws ParseException {
        Token name;
        List values=new ArrayList();
        Object val;
    // <ID> "(" value() ("," value())* ")"
            name = jj_consume_token(ID);
    jj_consume_token(23);
    val = value(false);
                                         values.add(val);
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 26:
        ;
        break;
      default:
        jj_la1[14] = jj_gen;
        break label_6;
      }
      jj_consume_token(26);
      val = value(false);
                                                                                  values.add(val);
    }
    jj_consume_token(25);
                {if (true) return new HuginFunction(name.image,values);}
    throw new Error("Missing return statement in function");
  }

  final private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  final private boolean jj_3_1() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  public HuginReaderTokenManager token_source;
  SimpleCharStream jj_input_stream;
  public Token token, jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  public boolean lookingAhead = false;
  private boolean jj_semLA;
  private int jj_gen;
  final private int[] jj_la1 = new int[15];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x3fc0,0x3fc0,0x20fc0,0x20fc0,0x20000,0x1000000,0x20000,0xc0,0xc0,0x700,0x20000,0x83c000,0x1c000,0x820000,0x4000000,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  public HuginReader(java.io.InputStream stream) {
     this(stream, null);
  }
  public HuginReader(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new HuginReaderTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public HuginReader(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new HuginReaderTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public HuginReader(HuginReaderTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  public void ReInit(HuginReaderTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  final private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }

  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  final public Token getToken(int index) {
    Token t = lookingAhead ? jj_scanpos : token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.Vector jj_expentries = new java.util.Vector();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Enumeration e = jj_expentries.elements(); e.hasMoreElements();) {
        int[] oldentry = (int[])(e.nextElement());
        if (oldentry.length == jj_expentry.length) {
          exists = true;
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.addElement(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[27];
    for (int i = 0; i < 27; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 15; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 27; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  final public void enable_tracing() {
  }

  final public void disable_tracing() {
  }

  final private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  final private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
