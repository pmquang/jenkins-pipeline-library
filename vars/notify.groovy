#!/usr/bin/groovy

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.net.URLEncoder;

def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def color = ''

    println config.title_link

    if (currentBuild.currentResult=='SUCCESS') {
    	color = 'good'
    } else {
    	color = 'danger'
    }

    JSONArray attachments = new JSONArray();
	JSONObject attachment = new JSONObject();
/*	attachment.put('text', "${config?.message}");
	attachment.put('fallback', "${config?.message}");
	attachment.put('color', color);
	attachment.put('title',"${config?.title}");
	attachment.put('title_link',"${config?.title_link}");
	attachments.add(attachment);
*/
	attachment.put('text', "hello");
	attachment.put('fallback', "moto");
	attachment.put('color', color);
	attachment.put('title',"okie");
	attachment.put('title_link', URLEncoder.encode(config.title_link, "UTF-8"));
	attachments.add(attachment)
    slackSend(color: 'good', channel: "#${config.slackChannel}", attachments: attachments.toString())
}
