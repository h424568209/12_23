import java.util.*;

public class LeeCode {
    /**
     * 给定一个整数数组和一个整数 k, 你需要在数组里找到不同的 k-diff 数对。
     * 这里将 k-diff 数对定义为一个整数对 (i, j), 其中 i 和 j 都是数组中的数字，且两数之差的绝对值是 k.
     * @param nums 数组
     * @param k 绝对值
     * @return  组数
     */
    private int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        int left = 0 ;
        int right = 1;
        while(right<nums.length){
            int t = nums[right] - nums[left];
            if(t > k){
                left ++;
            }else if(k > t){
                right ++;
            }else{
                count ++;
                right++;
                left++;
            }
            //left >0 是 保证了是第一个元素的时候 left-1数组不越界
            while(left >0 && left < nums.length &&nums[left] == nums[left-1] ){
                left++;
            }
            while( right< nums.length&&nums[right] == nums[right-1] ){
                right++;
            }
            //防止k==0的时候， left和right指向同一个数字
            if(right<=left){
                right = left+1;
            }
        }
        return count;
    }
    /**
     * 判断数组中是否能构成环状
     * @param nums 数组
     * @return true/false
     */
    public boolean circularArrayLoop(int[] nums) {
        int n = nums.length;
        if(n==1)
            return false;
        boolean []visit = new  boolean[n];
        Arrays.fill(visit,false);
        int i =0;
        //遍历整个数组，查看以i为下标做起点，是否存在循环
        while(i<n){
            int j = i;
            //循环链  用来判断循环
            ArrayList<Integer> loopLink = new ArrayList<>();
            //进入循环链，判断为起点的下标的循环链中是否符合循环条件

            while(true){
                visit[j] = true;
                loopLink.add(j);
                //得到循环链中的下一个下标--索引
                int next = j + nums[j];
                while(next >=n || next < 0){
                    if(next >=n){
                        next %= n;
                    }else if(next < 0){
                        next = n +next;
                    }
                }
                //判断循环不成立
                //若循环链前后量元素为相反数，则不满足条件
                //若循环长度是1，或者进入死循环，不满足循环
                if( j == next || nums[j] * nums[next] <0)
                    break;
                //判断循环成立
                //进入死循环，长度大于1满足循环
                if(loopLink.contains(next))
                    return true;
                j = next;
            }
            //已经在循环链上走过的点，不用再遍历
            //从每一个节点入手，判断所有的情况
            while(visit[i]){
                i++;
                if(i>=n)
                    return false;
            }
            System.out.println(loopLink);
        }
        return false;
    }
    /**
     * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一个字母只会出现在其中的一个片段。返回一个表示每个字符串片段的长度的列
     * @param S 字符串
     * @return  字符串中每个片段的长度
     */
    public List<Integer> partitionLabels(String S) {
        //存放每个字母在字符串中最后一次出现的位置
       int[] last = new int[26];
       for(int i = 0 ; i < S.length() ; i++){
           last[S.charAt(i) - 'a']  = i ;
       }
       List<Integer> res=  new ArrayList<>();
        //preIndex 表示上个区间的右端点
        //maxIndex 表示当前遍历的字符最后出现的位置的最大值
       int preIndex = -1;
       int maxIndex = 0;
       for(int i =0 ; i <S.length() ;i++){
           int index = last[S.charAt(i) - 'a'];
           //更新区间的右端点，向右延申
           if(index > maxIndex){
               maxIndex = index;
           }
           //如果当前位置 i等于当前遍历的字符出现位置的最大值
           //说明maxIndex是当前区间的右端点
           if(i == maxIndex){
               //添加区间的长度
               res.add(maxIndex - preIndex);
               //保存当前的右端点
               preIndex = maxIndex;
           }
       }
        return res;
    }
    public static void main(String[] args) {
        LeeCode l = new LeeCode();
        System.out.println(l.partitionLabels("abcdefggc"));
        System.out.println(l.circularArrayLoop(new int[]{2,-1,1,2,2}));
        System.out.println(l.circularArrayLoop(new int[]{-1,2}));
        System.out.println(l.circularArrayLoop(new int[]{-2,1,-1,-2,-2}));
        System.out.println();
        System.out.println(l.circularArrayLoop(new int[]{3,1,2}));

        System.out.println(l.findPairs(new int[]{3,1,4,1,5},2));
        System.out.println(l.findPairs(new int[]{1,3,1,5,4},0));
    }
}
