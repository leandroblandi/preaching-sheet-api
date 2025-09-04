package org.jw.preechingsheet.api.services;

public interface IRateLimiterService {
	public boolean isRateLimited(String ip);
}
