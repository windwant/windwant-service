package org.windwant.algorithm.subjects;

import java.util.*;

/**
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。

 * 注意：

 * 答案中不可以包含重复的四元组。

 * 示例：

 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。

 * 满足要求的四元组集合为：
 * [
 * [-1,  0, 0, 1],
 * [-2, -1, 1, 2],
 * [-2,  0, 0, 2]
 * ]
 * Created by Administrator on 19-3-4.
 */
public class FourSum {

    /**
     * 四数之和
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Set<List<Integer>> sets = new HashSet();
        List<Integer> numList = new ArrayList();
        for (int num : nums) {
            numList.add(num);
        }
        for (int i = 0; i < numList.size(); i++) {
            List<Integer> copy = new ArrayList();
            copy.addAll(numList);
            copy.remove(numList.get(i));
            List<List<Integer>> threes = threeSum(copy, target - numList.get(i));
            final int finalI = i;
            threes.forEach(item -> {
                item.add(nums[finalI]);
                Collections.sort(item);
                sets.add(item);
            });
        }
        return new ArrayList(sets);
    }

    /**
     * 三数之和
     * @param nums
     * @param target
     * @return
     */
    public static List<List<Integer>> threeSum(List<Integer> nums, int target) {
        if(nums == null || nums.size() < 3) return new ArrayList();
        Set<List<Integer>> result = new HashSet<>();
        List<Integer> numList = new ArrayList();
        for (int num : nums) {
            numList.add(num);
        }

        for (Integer num : numList) {
            List<Integer> copy = new ArrayList();
            copy.addAll(numList);
            copy.remove(num);
            List<int[]> tmp = twoSum(copy, target - num);
            if(tmp.size()>0){
                for (int[] ints : tmp) {
                    List<Integer> list = new ArrayList(){{add(num);add(ints[0]);add(ints[1]);}};
                    Collections.sort(list);
                    result.add(list);
                }
            }
        }

        return new ArrayList(result);
    }

    /**
     * 两数之和
     * @param nums
     * @param target
     * @return
     */
    public static List<int[]> twoSum(List<Integer> nums, int target) {
        List<int[]> result = new ArrayList();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            int complement = target - nums.get(i);
            if (map.containsKey(complement)) {
                result.add(new int[] { complement, nums.get(i) });
            }
            map.put(nums.get(i), i);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, -1, 0, -2, 2};
//        int[] nums = {91277418,66271374,38763793,4092006,11415077,60468277,1122637,72398035,-62267800,22082642,60359529,-16540633,92671879,-64462734,-55855043,-40899846,88007957,-57387813,-49552230,-96789394,18318594,-3246760,-44346548,-21370279,42493875,25185969,83216261,-70078020,-53687927,-76072023,-65863359,-61708176,-29175835,85675811,-80575807,-92211746,44755622,-23368379,23619674,-749263,-40707953,-68966953,72694581,-52328726,-78618474,40958224,-2921736,-55902268,-74278762,63342010,29076029,58781716,56045007,-67966567,-79405127,-45778231,-47167435,1586413,-58822903,-51277270,87348634,-86955956,-47418266,74884315,-36952674,-29067969,-98812826,-44893101,-22516153,-34522513,34091871,-79583480,47562301,6154068,87601405,-48859327,-2183204,17736781,31189878,-23814871,-35880166,39204002,93248899,-42067196,-49473145,-75235452,-61923200,64824322,-88505198,20903451,-80926102,56089387,-58094433,37743524,-71480010,-14975982,19473982,47085913,-90793462,-33520678,70775566,-76347995,-16091435,94700640,17183454,85735982,90399615,-86251609,-68167910,-95327478,90586275,-99524469,16999817,27815883,-88279865,53092631,75125438,44270568,-23129316,-846252,-59608044,90938699,80923976,3534451,6218186,41256179,-9165388,-11897463,92423776,-38991231,-6082654,92275443,74040861,77457712,-80549965,-42515693,69918944,-95198414,15677446,-52451179,-50111167,-23732840,39520751,-90474508,-27860023,65164540,26582346,-20183515,99018741,-2826130,-28461563,-24759460,-83828963,-1739800,71207113,26434787,52931083,-33111208,38314304,-29429107,-5567826,-5149750,9582750,85289753,75490866,-93202942,-85974081,7365682,-42953023,21825824,68329208,-87994788,3460985,18744871,-49724457,-12982362,-47800372,39958829,-95981751,-71017359,-18397211,27941418,-34699076,74174334,96928957,44328607,49293516,-39034828,5945763,-47046163,10986423,63478877,30677010,-21202664,-86235407,3164123,8956697,-9003909,-18929014,-73824245};
        System.out.println(fourSum(nums, 0));
    }
}
