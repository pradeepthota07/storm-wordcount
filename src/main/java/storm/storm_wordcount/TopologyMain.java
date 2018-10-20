package storm.storm_wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class TopologyMain {

	public static void main(String[] args) throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-counter", new WordCounter(), 3).fieldsGrouping("word-reader", new Fields("word"));

		StormTopology topology = builder.createTopology();

		Config conf = new Config();
		conf.setDebug(true);
		conf.put("fileToRead", "/home/centos/Downloads/read.txt");
		conf.put("dirToWrite", "/home/centos/Downloads/");

//		LocalCluster cluster = new LocalCluster();

		try {
			StormSubmitter.submitTopology("Word-Count-Topology", conf, topology);
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
