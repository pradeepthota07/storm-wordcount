package storm.example1;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class TopologyMain {

	public static void main(String[] args) throws InterruptedException{
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("Yahoo-Fianace-Spout", new Spout());
		builder.setBolt("Yahoo-Finance-Bolt", new Bolt()).shuffleGrouping("Yahoo-Fianace-Spout");
		
		StormTopology topology = builder.createTopology();
		
		Config conf = new Config();
		conf.setDebug(true);
		conf.put("fileToWrite", "C:\\thota\\output.txt");
		
//		LocalCluster cluster = new LocalCluster();
		
		try {
			StormSubmitter.submitTopology("Yahoo-Finanace-Topology", conf, topology);
		} catch (AlreadyAliveException e) {
			e.printStackTrace();
		} catch (InvalidTopologyException e) {
			e.printStackTrace();
		} catch (AuthorizationException e) {
			e.printStackTrace();
		}
		Thread.sleep(50000);
//		cluster.shutdown();
		
	}

}
