import java.util.*;

/* 
 * Here are all LeetCode practices
 */
public class Solution {
	
	// Definition for singly-linked list.
	public class ListNode {
		int val;
		ListNode next;
		ListNode() {}
		ListNode(int val) { this.val = val; }
		ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	}
	
	// Definition for tree node.
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode() {}
		TreeNode(int val) { this.val = val; }
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
	
	// Given two binary strings a and b, return their sum as a binary string.
	public String addBinary(String a, String b) {
		if (a == null || a.length() == 0) return b;
		if (b == null || b.length() == 0) return a;
		
		StringBuilder res = new StringBuilder();
		int aLast = a.length() - 1;
		int bLast = b.length() - 1;
		int carry = 0;
		
		while (aLast >= 0 || bLast >= 0) {
			int sum = carry;
			if (aLast >= 0) {
				sum += a.charAt(aLast) - '0';
				aLast--;
			}
			if (bLast >= 0) {
				sum += b.charAt(bLast) - '0';
				bLast--;
			}
			
			carry = sum > 1 ? 1 : 0;
			res.append(sum % 2);
		}
		
		if (carry != 0) res.append(carry);
		return res.reverse().toString();
	}
	
	// Given a non-negative integer x, compute and return the square root of x.
	public int mySqrt(int x) {
		if (x == 0) return 0;
		
		int start = 1, end = x;
		while (start < end) {
			int mid = start + (end - start) / 2;
			// If mid^2 already equals to x
			if (mid <= x / mid && (mid + 1) > x / (mid + 1)) {
				return mid;
			} else if (mid > x / mid) { // Continues to look in the left side.
				end = mid;
			} else { // Continues to look in the right side.
				start = mid + 1;
			}
		}
		return start;
	}
	
	/*
	 * You are climbing a staircase. It takes n steps to reach the top.
	 * Each time you can either climb 1 or 2 steps.
	 * In how many distinct ways can you climb to the top?
	 */
	public int climbStairs(int n) {
		if (n == 0) return 1;
		
        int a = 0;
        int b = 1;
        int res = 0;
        
        while (n > 0) {
        	res = a + b;
        	a = b;
        	b = res;
        	n--;
        }
		
		return res;
    }
	
	/*
	 * Given the head of a sorted linked list, delete all duplicates such that each element appears only once.
	 * Return the linked list sorted as well.
	 */
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null) return head;
		
		ListNode curr = head;
		ListNode temp = curr.next;
		
		while (temp != null) {
			if (curr.val != temp.val) {
				curr.next = temp;
				curr = curr.next;
				temp = temp.next;
			} else {
				temp = temp.next;
				curr.next = null;
			}
		}
		return head;
	}
	
	/*
	 * Merge Sorted Array
	 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
	 */
	public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Two pointers point toward the end of each array.
		int index1 = m - 1;
		int index2 = n - 1;
		
		// Starts iterate from the back of nums1 array.
		// This way to avoids overwritten nums1 array or having to make a copy of nums1 array.
		for (int i = m + n - 1; i >= 0; i--) {
			if (index2 < 0) break; // If runs out of index on nums2 array, no need to modify nums1 array anymore.
			if (index1 >= 0 && (nums1[index1] > nums2[index2])) {
				nums1[i] = nums1[index1];
				index1--;
			} else {
				nums1[i] = nums2[index2];
				index2--;
			}
		}
    }
	
	/*
	 * Given the root of a binary tree, return the inorder traversal of its nodes' values.
	 */
	
	// Recursive.
	public List<Integer> inorderTraversalRec(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		inorderTraversalRecHelper(root, list);
		
		return list;
	}
	
	// Private helper method to help with recursion.
	private void inorderTraversalRecHelper(TreeNode root, List<Integer> list) {
		if (root != null) {
			inorderTraversalRecHelper(root.left, list);
			list.add(root.val);
			inorderTraversalRecHelper(root.right, list);
		}
	}
	
	// Using stack.
	public List<Integer> inorderTraversalStack(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		Stack<TreeNode> stack = new Stack<>();
		TreeNode curr = root;
		
		while (curr != null || !stack.isEmpty()) {
			// Keep adding the left branch of the node into the list and traverse down the tree.
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}
			
			curr = stack.pop(); // Traverses up to a higher node.
			list.add(curr.val); // Add the result into the list.
			curr = curr.right; // Traverse down the right branch of the node.
		}
		return list;
	}
	
	/*
	 * Given the roots of two binary trees p and q, write a function to check if they are the same or not.
	 * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
	 */
	
	// Recursive.
	public boolean isSameTreeRec(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true; // Return true if both trees are null.
		if (p == null || q == null) return false; // Return false if only one of the trees is null.
		if (p.val != q.val) return false; // Return false if any of the tree values are different.
		return isSameTreeRec(p.left, q.left) && isSameTreeRec(p.right, q.right);
	}
	
	// Iterative.
	public boolean isSameTreeIter(TreeNode p, TreeNode q) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(p);
		queue.offer(q);
		
		while (!queue.isEmpty()) {
			TreeNode temp1 = queue.poll();
			TreeNode temp2 = queue.poll();
			
			if (temp1 == null && temp2 == null) continue;
			if (temp1 == null || temp2 == null) return false;
			if (temp1.val != temp2.val) return false;
			
			queue.offer(temp1.left);
			queue.offer(temp2.left);
			queue.offer(temp1.right);
			queue.offer(temp2.right);
		}
		return true;
	}
	
	/*
	 * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
	 */
	
	// Recursive.
	public boolean isSymmetricRec(TreeNode root) {
		return isSymmetricHelper(root, root);
	}
	
	// Helper for isSymmetric.
	private boolean isSymmetricHelper(TreeNode root1, TreeNode root2) {
		if (root1 == null && root2 == null) return true;
		if (root1 == null || root2 == null) return false;
		if (root1.val != root2.val) return false;
		return isSymmetricHelper(root1.left, root2.right) && isSymmetricHelper(root1.right, root2.left);
	}
	
	// Iterative.
	public boolean isSymmetricIter(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			TreeNode temp1 = queue.poll();
			TreeNode temp2 = queue.poll();
			
			if (temp1 == null && temp2 == null) continue;
			if (temp1 == null || temp2 == null) return false;
			if (temp1.val != temp2.val) return false;
			
			queue.offer(temp1.left);
			queue.offer(temp2.right);
			queue.offer(temp1.right);
			queue.offer(temp2.left);
		}
		
		return true;
	}
	
	/*
	 * Given the root of a binary tree, return its maximum depth.
	 * A binary tree's maximum depth is the number of nodes along the longest path
	 * from the root node down to the farthest leaf node.
	 */
	
	// Recursive.
	public int maxDepthRec(TreeNode root) {
		if (root == null) return 0;
		
		int leftHeight = maxDepthRec(root.left);
		int rightHeight = maxDepthRec(root.right);
		return 1 + Math.max(leftHeight, rightHeight);
	}
	
	// Iterative DFS using stack.
	public int maxDepthDFS(TreeNode root) {
		if (root == null) return 0;
		
		Stack<TreeNode> stack = new Stack<>();
		Stack<Integer> depth = new Stack<>();
		stack.push(root);
		depth.push(1); // root counts as 1.
		int max = Integer.MIN_VALUE;
		
		while (!stack.isEmpty()) {
			TreeNode curr = stack.pop();
			int currDepth = depth.pop();
			max = Math.max(max, currDepth);
			
			// PUSH right TreeNode in stack first so when POP, left TreeNode would be searched first.
			if (curr.right != null) {
				stack.push(curr.right);
				depth.push(currDepth + 1);
			}
			
			if (curr.left != null) {
				stack.push(curr.left);
				depth.push(currDepth + 1);
			}
		}
		return max;
	}
	
	// Iterative BFS using queue.
	public int maxDepthBFS(TreeNode root) {
		if (root == null) return 0;
		
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		int depth = 0;
		
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size > 0) {
				TreeNode curr = queue.poll();
				if (curr.left != null) queue.offer(curr.left);
				if (curr.right != null) queue.offer(curr.right);
				size--;
			}
			depth++;
		}
		return depth;
	}
	
	/*
	 * Given an integer array nums where the elements are sorted in ascending order, 
	 * convert it to a height-balanced binary search tree.
	 * 
	 * A height-balanced binary tree is a binary tree in which the depth of the two 
	 * subtrees of every node never differs by more than one.
	 */
	
	// Recursive.
	public TreeNode sortedArrayToBSTRec(int[] nums) {
		if (nums == null || nums.length == 0) return null;
		
		return sortedArrayToBSTHelperRec(nums, 0, nums.length - 1);
	}
	
	// Helper method for sortedArrayToBSTRec.
	private TreeNode sortedArrayToBSTHelperRec(int[] nums, int left, int right) {
		if (left > right) return null;
		
		int mid = left + (right - left) / 2;
		TreeNode root = new TreeNode(nums[mid]);
		root.left = sortedArrayToBSTHelperRec(nums, left, mid - 1);
		root.right = sortedArrayToBSTHelperRec(nums, mid + 1, right);
		return root;
	}
	
	// Iterative DFS using stack.
	public TreeNode sortedArrayToBSTDFS(int[] nums) {
		if (nums == null || nums.length == 0) return null;
		
		Stack<myNode> stack = new Stack<>();
		int left = 0;
		int right = nums.length - 1;
		int val = nums[left + (right - left) / 2];
		TreeNode root = new TreeNode(val);
		stack.push(new myNode(root, left, right));
		
		while (!stack.isEmpty()) {
			myNode curr = stack.pop();
			int mid = curr.left + (curr.right - curr.left) / 2;
			
			if (mid != curr.left) {
				TreeNode leftChild = new TreeNode(nums[curr.left + (mid - 1 - curr.left) / 2]);
				curr.node.left = leftChild;
				stack.push(new myNode(leftChild, curr.left, mid - 1));
			}
			
			if (mid != curr.right) {
				TreeNode rightChild = new TreeNode(nums[mid + 1 + (curr.right - (mid + 1)) / 2]);
				curr.node.right = rightChild;
				stack.push(new myNode(rightChild, mid + 1, curr.right));
			}
		}
		return root;
	}
	
	// Iterative BFS using queue.
	public TreeNode sortedArrayToBSTBFS(int[] nums) {
		if (nums == null || nums.length == 0) return null;
		
		Queue<myNode> queue = new LinkedList<>();
		int left = 0;
		int right = nums.length - 1;
		int val = nums[left + (right - left) / 2];
		TreeNode root = new TreeNode(val);
		queue.offer(new myNode(root, left, right));
		
		while (!queue.isEmpty()) {
			myNode curr = queue.poll();
			int mid = curr.left + (curr.right - curr.left) / 2;
			
			if (mid != curr.left) {
				TreeNode leftChild = new TreeNode(nums[curr.left + (mid - 1 - curr.left) / 2]);
				curr.node.left = leftChild;
				queue.offer(new myNode(leftChild, curr.left, mid - 1));
			}
			
			if (mid != curr.right) {
				TreeNode rightChild = new TreeNode(nums[mid + 1 + (curr.right - (mid + 1)) / 2]);
				curr.node.right = rightChild;
				queue.offer(new myNode(rightChild, mid + 1, curr.right));
			}
		}
		return root;
	}
	
	// Definition for helper myNode;
	public class myNode {
		TreeNode node;
		int left, right;
		public myNode(TreeNode node, int left, int right) {
			this.node = node;
			this.left = left;
			this.right = right;
		}
	}
	
	/*
	 * Given a binary tree, determine if it is height-balanced.
	 * For this problem, a height-balanced binary tree is defined as:
	 * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
	 */
	
	// Top-down recursive.
	public boolean isBalancedTopDown(TreeNode root) {
		
		if (root == null) return true;
		
		int left = isBalancedTopDownHelper(root.left);
		int right = isBalancedTopDownHelper(root.right);
		return (Math.abs(left - right) <= 1) && isBalancedTopDown(root.left) && isBalancedTopDown(root.right);
	}
	
	private int isBalancedTopDownHelper(TreeNode node) {
		
		if (node == null) return 0;
		
		int left = isBalancedTopDownHelper(node.left);
		int right = isBalancedTopDownHelper(node.right);
		return 1 + Math.max(left, right);
	}
	
	// Bottom-up recursive.
	public boolean isBalancedBottomUp(TreeNode root) {
		
		if (isBalancedBottomUpHelper(root) == -1) return false;
		return true;
	}
	
	private int isBalancedBottomUpHelper(TreeNode node) {
		
		if (node == null) return 0;
		
		int left = isBalancedBottomUpHelper(node.left);
		if (left == -1) return -1;
		
		int right = isBalancedBottomUpHelper(node.right);
		if (right == -1 || Math.abs(left - right) > 1) return -1;
		
		return 1 + Math.max(left, right);
	}
	
	/*
	 * Given a binary tree, find its minimum depth.
	 * The minimum depth is the number of nodes along the
	 * shortest path from the root node down to the nearest leaf node.
	 */
	
	// Recursive DFS (inefficient).
	public int minDepthRec(TreeNode root) {
		
		if (root == null) return 0;
		
		int left = minDepthRec(root.left);
		int right = minDepthRec(root.right);
		if (left == 0) return 1 + right;
		if (right == 0) return 1 + left;
		return 1 + Math.min(left, right);
	}
	
	// Recursive BFS.
	public int minDepthRecBFS(TreeNode root) {
		
		if (root == null) return 0;
		
		if (root.left != null && root.right != null) return Math.min(minDepthRecBFS(root.left) + 1, minDepthRecBFS(root.right) + 1);
		if (root.left != null) return 1 + minDepthRecBFS(root.left);
		return 1 + minDepthRecBFS(root.right);
	}
	
	// Iterative BFS using queue.
	public int minDepthIterBFS(TreeNode root) {
		
		if (root == null) return 0;
		
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		int depth = 1;
		
		while (!queue.isEmpty()) {
			int size = queue.size();
			while (size > 0) {
				TreeNode curr = queue.poll();
				if (curr != null) {
					if (curr.left == null & curr.right == null) return depth;
					queue.offer(curr.left);
					queue.offer(curr.right);
				}
				size--;
			}
			depth++;
		}
		return depth;
	}
	
	/*
	 * Given the root of a binary tree and an integer targetSum, 
	 * return true if the tree has a root-to-leaf path such that 
	 * adding up all the values along the path equals targetSum.
	 */
	
	// Recursive.
	public boolean hasPathSumRec(TreeNode root, int targetSum) {
		
		if (root == null) return false;
		if (root.left == null && root.right == null) return root.val == targetSum;
		return hasPathSumRec(root.left, targetSum - root.val) || hasPathSumRec(root.right, targetSum - root.val);
	}
	
	// DFS Iterative using stack.
	public boolean hasPathSumIter(TreeNode root, int targetSum) {
		
		if (root == null) return false;
		if (root.left == null && root.right == null) return root.val == targetSum;
		
		Stack<TreeNode> nodes = new Stack<>();
		Stack<Integer> sums = new Stack<>();
		nodes.push(root);
		sums.push(targetSum - root.val);
		
		while (!nodes.isEmpty()) {
			TreeNode curr = nodes.pop();
			int remainSum = sums.pop();
			if (curr.left == null && curr.right == null && remainSum == 0) return true;
			
			if (curr.left != null) {
				nodes.push(curr.left);
				sums.push(remainSum - curr.left.val);
			}
			
			if (curr.right != null) {
				nodes.push(curr.right);
				sums.push(remainSum - curr.right.val);
			}
		}
		return false;
	}
}
