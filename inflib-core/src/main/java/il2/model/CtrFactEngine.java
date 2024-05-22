package il2.model;

import java.util.Map;
import edu.ucla.belief.BeliefNetwork;
import il2.util.IntSet;

import java.util.ArrayList;

public class CtrFactEngine {
    public double handleCtrFactQueryCanonical() {

    }

    public double handleCtrFactQuery() {
        // 1. create a graph with 4 copies of non-root variables.
        // Identify which ones are roots by reading the Directed Graph code, count the
        // number of evidence variables there are
        // Create number of variables number of each non root

        // 2. Intervene z in the first copy,
        // intervene v in the second copy,
        // intervene b in the third copy,
        // intervene d in the fourth copy.

        // 3. The counterfactual query now becomes an associational query Pr(w,u|a,c) on
        // the new graph
        // (w in the first copy, u in the second copy, a in the third copy, c in the
        // fourth copy).
        if (w && u )
        // each variable is a list of values
        // 4. How to compute Pr(w,u|a,c)? Pr(w,u|a,b) = Pr(w,u,a,c) / Pr(a,c)
        // both Pr(w,u,a,c) and Pr(a,c) are in the form of Pr(e) (e being the evidence)
        // which can be computed using SamIam.
        double probability = 0;
        return probability;
    }
    // il2: converter for bayesian into a bleief

    public BayesianNetwork generateCtrFactNetwork(BayesianNetwork bn, Map<String, String> evidence) {
        int nodenumber = bn.cpts().length;
        ArrayList<Table> rootnodes = new ArrayList<>();
        ArrayList<Table> nonroots = new ArrayList<>();
        ArrayList<Table> newnodes = new ArrayList<>();
        // Find number of worlds
        // int numworld = find out by parsing query
        int numworld;

        // Identify roots and non roots
        for (int i = 0; i < nodenumber; i++) {
            if (bn.cpts()[i].vars().size() == 1) {
                rootnodes.add(bn.cpts()[i]);
            } else {
                Table cpt = bn.cpts()[i];
                for (int j = 0; j < numworld; j++) {
                    nonroots.add(bn.cpts()[i]);
                }
            }
        }
        // Domain size = roots + nworlds * nonroots
        int domainsize = rootnodes.size() + numworld * nonroots.size();
        // int domainsize = rootnodes.size() + (numworld - 1) * nonroots.size();
        Domain domain = new Domain(domainsize);
        int[] ids = new int[domainsize];

        // This is the table of tables we will use to construct a new bayesian network
        // Each table needs an intset containing parent ids
        Table[] tables = new Table[domainsize];

        // Make an array of arraylist<int> for the original nodes
        // so we know all the parent id index of each og node
        // this
        final int worldzerosize = rootnodes.size() + nonroots.size();
        ArrayList<Integer>[] array = new ArrayList[worldzerosize];

        // Add n copies of non roots to the domain
        // Make a list of ids for easy reference
        for (int i = 0; i < rootnodes.size(); i++) {
            Table root = rootnodes.get(i);
            int id = root.vars().get(root.vars().size() - 1);
            ids[i] = id;
            tables[i] = root;
        }
        for (int i = rootnodes.size(); i < nonroots.size() + rootnodes.size(); i++) {
            Table nonroot = nonroots.get(i - rootnodes.size());
            int id = nonroot.vars().get(nonroot.vars().size() - 1);
            ids[i] = id;
            tables[i] = nonroot;
        }
        // Store the relationship between world zero nodes in arraylist
        // Each array index will be an array list with all the indices of parents
        // array[0] to array[rootnodes.size()-1] will be empty as these are root nodes
        // with no parents
        for (int i = rootnodes.size(); i < nonroots.size() + rootnodes.size(); i++) {
            Table nonroot = nonroots.get(i - rootnodes.size());
            for (int j = 0; j < worldzerosize; j++) {
                int parentid = ids[j];
                if (nonroot.vars().contains(parentid)) {
                    array[i].add(j);
                }
            }
        }
        // create ids for all the new world nodes
        for (int i = worldzerosize; i < domainsize; i++) {
            String name = "";
            String[] value = new String[] { "Present", "Absent" }; // dummy values to be replaced
            int id = domain.addDim(name, value);
            ids[i] = id;
        }
        // Fill in the remaining new tables into array of tables
        for (int i = worldzerosize; i < domainsize; i++) {
            // Table nonroot = nonroots.get(i);
            int worldno = (i - rootnodes.size()) / nonroots.size();
            int placewithinworld = (i - rootnodes.size()) % nonroots.size();
            ArrayList<Integer> parentrelations = array[rootnodes.size() + placewithinworld];
            int[] parentids = new int[parentrelations.size()];
            for (int j = 0; j < parentrelations.size(); i++) {
                int index = parentrelations.get(j);
                parentids[j] = ids[rootnodes.size() - 1 + index + worldno * nonroots.size()];
            }
            // for (int j = 0; j < numworld; j++){
            // String name;
            // String[] values;
            // int id+
            // }
            // make intset
            // make table
            // new int[]
            tables[i] = new Table(domain, new IntSet(parentids));
        }
        // Rename all the nodes to numeric
        return new BayesianNetwork(tables);
    }

    // MAP IMPLEMENTATION:

}
