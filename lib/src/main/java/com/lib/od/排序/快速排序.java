package com.lib.od.排序;

public class 快速排序 {

    public void quickSort(int[] nums,int start,int end){

        int i = start;
        int j = end;
        if (i>=j){
            return;
        }
        int key = nums[start];
        while (i!=j){
            while (i<j && nums[j]>=key){
                j--;
            }
            while (i<j && nums[i]<=key){
                i++;
            }

            if (i<j){
                swap(nums,i,j);
            }
        }

        swap(nums,start,i);
        quickSort(nums, start, i-1);
        quickSort(nums, i+1, end);

    }

    private void swap(int[] nums,int x,int y){
        int tmp = nums[x];
        nums[x] = nums[y];
        nums[y] = tmp;
    }



    public void myQuickSort(int[] nums,int start,int end){

        int i = start;
        int j = end;
        if (i>j){
            return;
        }

        int key = nums[start];

        while (i!=j){
            while (key<nums[i] && i<j){
                i++;
            }
            while (key>nums[j]&& i<j){
                j--;
            }
            if (i<j){
                swap(nums, i,j);
            }
        }
        swap(nums, start, i);
        myQuickSort(nums, start,i-1);
        myQuickSort(nums, i+1,end);
    }








    // 排序入口
    public static void mergeSort(int[] A) {
        sort(A, 0, A.length - 1);
    }

    //递归
    public static void sort(int[] A, int start, int end) {
        if (start >= end)
            return;
        // 找出中间索引
        int mid = (start + end) / 2;
        // 对左边数组进行递归
        sort(A, start, mid);
        // 对右边数组进行递归
        sort(A, mid + 1, end);
        // 合并
        merge(A, start, mid, end);

    }

    // 将两个数组进行归并，归并前面2个数组已有序，归并后依然有序
    public static void merge(int[] A, int start, int mid, int end) {
        int[] temp = new int[A.length];// 临时数组
        int k = 0;
        int i = start;
        int j = mid + 1;
        while (i <= mid && j <= end) {
            // 从两个数组中取出较小的放入临时数组
            if (A[i] <= A[j]) {
                temp[k++] = A[i++];
            } else {
                temp[k++] = A[j++];
            }
        }
        // 剩余部分依次放入临时数组（实际上两个while只会执行其中一个）
        while (i <= mid) {
            temp[k++] = A[i++];
        }
        while (j <= end) {
            temp[k++] = A[j++];
        }
        // 将临时数组中的内容拷贝回原数组中 （left-right范围的内容）
        for (int m = 0; m < k; m++) {
            A[m + start] = temp[m];
        }
    }

}
