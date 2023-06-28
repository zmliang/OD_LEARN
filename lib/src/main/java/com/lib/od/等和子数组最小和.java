package com.lib.od;


import java.util.Arrays;

/**
 * <p>给定一个数组nums，将元素分为若干个组，使得每组和相等，求出满足条件的所有分组中，组内元素和的最小值。</p>
 *
 * 回溯算法
 *
 */
public class 等和子数组最小和 extends BaseTest{//TODO

    @Override
    protected void officialSolution() {
        int n = scanner.nextInt(); // 输入数组长度
        int[] nums = new int[n]; // 创建数组
        int sum = 0; // 记录数组元素和
        for (int i = 0; i < n; i++) { // 循环读入数组元素
            nums[i] = scanner.nextInt();
            sum += nums[i]; // 累加数组元素和
        }

        // 最大可以等分为 m 个子数组
        for (int m = n; m > 0; m--) { // 从最大的可能行开始，逐步减小子数组个数
            if (canPartitionKSubsets(nums, m, sum)) { // 判断是否能将数组等分为 m 个子数组
                System.out.println(sum / m); // 输出每个子数组的元素和
                break; // 找到最小的情况后跳出循环
            }
        }
    }

    public static boolean canPartitionKSubsets(int[] nums, int k, int sum) {
        if (sum % k != 0) { // 如果数组元素和不能被 k 整除，返回 false
            return false;
        }
        int target = sum / k; // 计算每个子数组的元素和
        int n = nums.length;
        if (nums[n - 1] > target) { // 如果最大元素大于每个子数组的元素和，返回 false
            return false;
        }
        boolean[] used = new boolean[n]; // 创建一个布尔数组，用于记录数组元素是否被使用
        Arrays.sort(nums); // 将数组排序，从大到小进行回溯
        return backtrack(nums, used, n - 1, 0, target, k); // 调用回溯函数进行判断
    }

    private static boolean backtrack(int[] nums, boolean[] used, int index, int curSum, int target, int k) {
        if (k == 0) { // 如果已经将数组等分为 k 个子数组，返回 true
            return true;
        }
        if (curSum == target) { // 如果当前子数组的元素和等于目标值，递归调用回溯函数，开始下一个子数组的划分
            return backtrack(nums, used, nums.length - 1, 0, target, k - 1);
        }
        for (int i = index; i >= 0; i--) { // 从大到小遍历数组元素
            if (used[i]) { // 如果当前元素已经被使用，跳过
                continue;
            }
            if (curSum + nums[i] > target) { // 如果当前子数组的元素和加上当前元素大于目标值，跳过
                break;
            }
            used[i] = true; // 标记当前元素已被使用
            if (backtrack(nums, used, i - 1, curSum + nums[i], target, k)) { // 递归调用回溯函数，继续划分当前子数组
                return true;
            }
            used[i] = false; // 回溯，将当前元素标记为未使用
        }
        return false; // 如果无法划分出 k 个子数组，返回 false
    }

    @Override
    protected void mySolution() {
        int k = 0;
        int[] nums = new int[]{};

        int total = Arrays.stream(nums).sum();



    }


}
