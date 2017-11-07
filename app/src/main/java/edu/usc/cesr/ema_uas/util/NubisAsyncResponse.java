package edu.usc.cesr.ema_uas.util;

public interface NubisAsyncResponse {
	void processFinish(String output, int responseCode, String responseString, NubisDelayedAnswer delayedAnswer, int deleteId);
}
