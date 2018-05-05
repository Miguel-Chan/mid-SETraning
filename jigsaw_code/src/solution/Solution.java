package solution;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;

import java.util.*;


/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {

    private Queue<JigsawNode> exploreList;	// 用以保存已发现但未访问的节点
    private Set<JigsawNode> visitedList;	// 用以保存已发现的节点

    private List<JigsawNode> solutionPath;// 解路径：用以保存从起始状态到达目标状态的移动路径中的每一个状态节点
    private int searchedNodesNum;

	/**
	 * 拼图构造函数
	 */
	public Solution() {

	}

	/**
	 * 拼图构造函数
	 * @param bNode - 初始状态节点
	 * @param eNode - 目标状态节点
	 */
	public Solution(JigsawNode bNode, JigsawNode eNode) {
		super(bNode, eNode);
	}


	/**
	 *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
	 * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
	 * @return 搜索成功时为true,失败为false
	 */
	public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
        exploreList = new LinkedList<>();
        visitedList = new HashSet<>();
        beginJNode = new JigsawNode(bNode);
        endJNode = new JigsawNode(eNode);
        currentJNode = null;

        final int DIR = 4;
        searchedNodesNum = 0;
        solutionPath = null;

        exploreList.add(beginJNode);

        while (!exploreList.isEmpty()) {
            searchedNodesNum++;

            currentJNode = exploreList.poll();

            if (currentJNode.equals(endJNode)) {
                getPath();
                break;
            }

            JigsawNode[] nextNodes = new JigsawNode[] {
                    new JigsawNode(currentJNode), new JigsawNode(currentJNode),
                    new JigsawNode(currentJNode), new JigsawNode(currentJNode)
            };

            for (int i = 0; i < DIR; i++) {
                if (nextNodes[i].move(i) && !visitedList.contains(nextNodes[i])) {
                    exploreList.add(nextNodes[i]);
                    visitedList.add(nextNodes[i]);
                }
            }
        }

        System.out.println("Jigsaw BFSearch Result:");
        System.out.println("Begin state:" + this.getBeginJNode().toString());
        System.out.println("End state:" + this.getEndJNode().toString());
        //System.out.println("Solution Path: ");
        //System.out.println(this.getSolutionPath());
        System.out.println("Total number of searched nodes:" + searchedNodesNum);
        System.out.println("Depth of the current node is:" + this.getCurrentJNode().getNodeDepth());
        return this.isCompleted();
	}


	/**
	 *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
	 * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
	 * @param jNode - 要计算代价估计值的节点
	 */
	public void estimateValue(JigsawNode jNode) {
		int s = 0; // 1.后续节点不正确的数码个数
        int distance = 0; //2. 放错位的数码与其正确位置的距离
        int difference = 0;
        int wrongPos = 0;
        int dimension = JigsawNode.getDimension();
		for (int index = 1; index < dimension * dimension; index++) {
			if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
				s++;
			}
			if (jNode.getNodesState()[index] != 0 &&
                    jNode.getNodesState()[index] != endJNode.getNodesState()[index]) {
                wrongPos++;
                int endIndex = 1;
                for (int i = 1; i < endJNode.getNodesState().length; i++) {
                    if (endJNode.getNodesState()[i] == jNode.getNodesState()[index]) {
                        endIndex = i;
                        break;
                    }
                }

                int x1 = (endIndex - 1) / dimension;
                int y1 = (endIndex - 1) % dimension;
                int x2 = (index - 1) / dimension;
                int y2 = (index - 1) % dimension;

                difference += Math.abs(x1 - x2) + Math.abs(y1 - y2);
                distance += (int)Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1- y2), 2));
            }
		}
		jNode.setEstimatedValue(s * 2 + distance * 3 + difference + wrongPos);
	}
}
