package com.nationsky.callname;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CallName {

	// 线程钩子
	private boolean stat = true;
	// 判断线程是否启动
	private boolean isThread = false;
	// 主面板
	JFrame rFrame = new JFrame("MEAP-IM");
	// 名称显示
	JLabel name = new JLabel();
	// 提示标签
	JLabel jt = new JLabel();
	// 启动按钮
	JButton start = new JButton("开始");
	// 结束按钮
	JButton end = new JButton("点名");
	// 采用的是伪随机数
	Random rd = new Random();

	public void init() throws Exception {
		// 获取名称
		 final ArrayList<String> callName = getNames();
		// 设置标签居中
		jt.setHorizontalAlignment(SwingConstants.CENTER);
		// 设置字体大小
		jt.setFont(new java.awt.Font("", 1, 15));
		// 设置名字显示的标签居中
		name.setHorizontalAlignment(SwingConstants.CENTER);
		// 通过匿名类实现Action按钮的监听事件
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 标识为设为true,随机获取名字
				stat = true;
				jt.setText("人生没有彩排，每一天都是现场直播");
				if (!isThread) {
					// 获取随机的姓名
					new Thread(new Runnable() {
						@Override
						public void run() {
							while (stat) {
								isThread = true;
								int a = rd.nextInt(callName.size());
								// 设置name标签的文字
								name.setText(callName.get(a));
								// 设置字体
								name.setFont(new java.awt.Font("", 1, 35));
								// 设置字体颜色
								name.setForeground(Color.PINK);
								try {
									//停留0.1秒
									TimeUnit.MILLISECONDS.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}).start();

				}
			}

		});
		//监听结束按钮
		end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//停止线程
				stat = false;
				//标识为设为false
				isThread = false;
				jt.setText("怀才就象怀孕，时间久了总会让人看出来");
			}
		});
		// 获取JFrame的面板
		Container p = this.rFrame.getContentPane();
		p.setLayout(new GridLayout(4, 1));
		p.add(jt, BorderLayout.NORTH);
		p.add(name, BorderLayout.CENTER);
		p.add(start, BorderLayout.AFTER_LINE_ENDS);
		p.add(end, BorderLayout.AFTER_LAST_LINE);
		// 调整大小，这个是java中无法设置标签的大小
		rFrame.setResizable(false);
		// 设置窗体大小
		rFrame.setSize(300, 300);
		// 设置可以显示
		rFrame.setVisible(true);
		rFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static ArrayList<String> getNames() throws Exception {
		Scanner cin = new Scanner(new FileInputStream(new File("name.txt")));
		ArrayList<String> nameList = new ArrayList<String>();
		cin.useDelimiter("\n");
		while (cin.hasNext()) {
			String s = cin.next();
			String[] split = s.split("\\s+");
			for (String name:split) {
				nameList.add(name);
			}
		}
		cin.close();
		return nameList;
	}
	
}