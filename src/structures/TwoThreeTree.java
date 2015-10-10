package structures;

/*
 *  2-node: one key, 2 kids
 *  3-node: two keys, 3 kids
 *  
 *  Insert: - go down till kids are null
 *          - if 2-node - make 3-node
 *          - if 3-node - make temp 4-node,
 *                        then bubble up the middle guy
 *                        continue to bubble up until invariant satisfied
 *                        if root is already 3-node - make middle guy a new root
 *                        and increase the height (the only way to increase the height)
 *          Splitting a 4-node is a local transformation, takes only const #ops.
 *          
 *  Invariant: every path from root to the null link is the same length!
 *             - Worst: lgN (all 2-nodes)
 *             - Best : log_3(N) (all 3-nodes)
 *             (example) 1B keys ~ 18-30 path length
 *             
 *  It is just a model - it's cumbersome to implement all the cases
 */
public class TwoThreeTree {
}

/*
 * B-Trees - general balanced tree. Nodes always between half-full and full
 * 
 * M-1 key link pairs per node (page size)
 * At least 2 key-link pairs at root
 * At least M/2 in other nodes
 * 
 * Search/Insertion O(log_M(N))
 */
