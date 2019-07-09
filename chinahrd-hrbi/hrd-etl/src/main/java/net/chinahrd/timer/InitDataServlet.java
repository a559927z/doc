package net.chinahrd.timer;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;






/**
 * 服务器启动后 自动加载 初始化数据的serlvet
 * @author htpeng
 *2017年4月11日下午5:58:11
 */
public class InitDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitDataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

   public void init(ServletConfig config) throws ServletException{
	  try {
	       	 Scheduler scheduler=new StdSchedulerFactory().getScheduler();
	       	 TimerModel timerModel =TimerModelFactory.create(new DefaultTimer().getTimerConfig());
	       	 scheduler.scheduleJob(timerModel.getJobDetail(),timerModel.getTrigger());
	       	 scheduler.startDelayed(1000);
	       	 scheduler.start();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
   }
	    
	    
}
