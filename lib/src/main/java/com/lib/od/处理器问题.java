package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 题目描述某公司研发了一款高性能AI处理器。每台物理设备具备8颗AI处理器，编号分别为0、1、2、3、4、5、6、7.编号0-3
 * 的处理器处于同一个链路中，编号4-7的处理器处于另外一个链路中，不通链路中的处理器不能通信。如下图所示。现给定服务
 * 器可用的处理器编号数组array，以及任务申请的处理器数量num，找出符合下列亲和性调度原则的芯片组合如果不存在符合要求的组合，则返回空列表。
 * 亲和性调度原则:
 * 如果申请处理器个数为1，则选择同一链路，剩余可用的处理器数量为1个的最佳，其次是剩余3个的为次佳，然后是剩余2个，最后是剩
 * 余4个
 * 如果申请处理器个数为2，则选择同一链路剩余可用的处理器数量2个的为最佳，其次是剩余4个，最后是剩余3个如果申请处理器个数为4，则必须选择同一链路剩余可用的外理器数量为4个如果申请处理器个数为8，则申请节点所有8个处理器。提示:
 * 1.任务申请的处理器数量只能是1、2、4、8。
 * 2.编号0-3的处理器处于一个链路，编号4-7的处理器处于另外一个链路3.处理器编号唯一，且不存在相同编号处理器。
 *
 *
 * 题目解析
 *
 * 用例中，链路link1=[0,11，链路link2=[4.5.6.7
 * 现在要选1个处理器，则需要按照亲和性调度原则
 * 如果申请处理器个数为1，则选择同一链路，剩余可用的处理器数量为1个的最佳，其次是剩余3个的为次佳，然后是剩余2个，最后是剩余4个
 * 最佳的是，找剩余可用1个处理器的链路，发现没有，link1剩余可用2，link2剩余可用4
 * 其次的是，找剩余可用3个处理器的链路，发现没有
 * 再次的是，找剩余可用2个处理器的链路，link1符合要求，即从0和1处理器中任选一个，有两种选择，可以使用dfs找对应组合
 */
public class 处理器问题 extends BaseTest{
    // 定义 getResult 方法，用于计算可用处理器组合情况
    public static String getResult(List<Integer> availableProcessors, int num) {
        // 定义两个列表，用于存放处理器数量小于 4 和大于等于 4 的处理器
        List<Integer> link1 = new ArrayList<>();
        List<Integer> link2 = new ArrayList<>();

        // 对可用处理器列表进行排序，并将处理器数量小于 4 的处理器加入 link1 列表，大于等于 4 的处理器加入 link2 列表
        availableProcessors.sort(Integer::compareTo);
        for (Integer processor : availableProcessors) {
            if (processor < 4) {
                link1.add(processor);
            } else {
                link2.add(processor);
            }
        }

        // 定义一个列表，用于存放处理器组合情况
        List<List<Integer>> combinations = new ArrayList<>();

        // 根据 num 值的不同，计算不同的处理器组合情况
        switch (num) {
            case 1:
                if (link1.size() == 1 || link2.size() == 1) {
                    if (link1.size() == 1) {
                        dfs(link1, 0, 1, new ArrayList<>(), combinations);
                    }
                    if (link2.size() == 1) {
                        dfs(link2, 0, 1, new ArrayList<>(), combinations);
                    }
                } else if (link1.size() == 3 || link2.size() == 3) {
                    if (link1.size() == 3) {
                        dfs(link1, 0, 1, new ArrayList<>(), combinations);
                    }
                    if (link2.size() == 3) {
                        dfs(link2, 0, 1, new ArrayList<>(), combinations);
                    }
                } else if (link1.size() == 2 || link2.size() == 2) {
                    if (link1.size() == 2) {
                        dfs(link1, 0, 1, new ArrayList<>(), combinations);
                    }
                    if (link2.size() == 2) {
                        dfs(link2, 0, 1, new ArrayList<>(), combinations);
                    }
                } else if (link1.size() == 4 || link2.size() == 4) {
                    if (link1.size() == 4) {
                        dfs(link1, 0, 1, new ArrayList<>(), combinations);
                    }
                    if (link2.size() == 4) {
                        dfs(link2, 0, 1, new ArrayList<>(), combinations);
                    }
                }
                break;
            case 2:
                if (link1.size() == 2 || link2.size() == 2) {
                    if (link1.size() == 2) {
                        dfs(link1, 0, 2, new ArrayList<>(), combinations);
                    }
                    if (link2.size() == 2) {
                        dfs(link2, 0, 2, new ArrayList<>(), combinations);
                    }
                } else if (link1.size() == 4 || link2.size() == 4) {
                    if (link1.size() == 4) {
                        dfs(link1, 0, 2, new ArrayList<>(), combinations);
                    }
                    if (link2.size() == 4) {
                        dfs(link2, 0, 2, new ArrayList<>(), combinations);
                    }
                } else if (link1.size() == 3 || link2.size() == 3) {
                    if (link1.size() == 3) {
                        dfs(link1, 0, 2, new ArrayList<>(), combinations);
                    }
                    if (link2.size() == 3) {
                        dfs(link2, 0, 2, new ArrayList<>(), combinations);
                    }
                }
                break;
            case 4:
                if (link1.size() == 4 || link2.size() == 4) {
                    if (link1.size() == 4) {
                        combinations.add(link1);
                    }
                    if (link2.size() == 4) {
                        combinations.add(link2);
                    }
                }
                break;
            case 8:
                if (link1.size() == 4 && link2.size() == 4) {
                    combinations.add(Stream.concat(link1.stream(), link2.stream())
                            .collect(Collectors.toList()));
                }
                break;
        }

        // 将处理器组合情况转换为字符串并返回
        return combinations.toString();
    }

    // 定义 dfs 方法，用于计算处理器组合情况
    public static void dfs(List<Integer> processors, int index, int level, List<Integer> path,
                           List<List<Integer>> combinations) {
        // 如果 path 列表中的元素数量等于 level，表示已经找到了一种处理器组合情况，将其加入 combinations 列表中
        if (path.size() == level) {
            combinations.add(new ArrayList<>(path));
        }

        // 遍历 processors 列表中 index 位置之后的元素，并将其加入 path 列表中，然后递归调用 dfs 方法
        for (int i = index; i < processors.size(); i++) {
            path.add(processors.get(i));
            dfs(processors, i + 1, level, path, combinations);
            path.remove(path.size() - 1);
        }
    }



    @Override
    void officialSolution() {
        // 读取输入的可用处理器信息
        List<Integer> availableProcessors = Arrays.stream(scanner.nextLine().split("[\\[\\]\\,\\s]"))
                .filter(str -> !"".equals(str))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // 读取输入的 num 值
        int num = scanner.nextInt();

        // 调用 getResult 方法并输出结果
        System.out.println(getResult(availableProcessors, num));
    }

    @Override
    protected void mySolution() {

    }
}
