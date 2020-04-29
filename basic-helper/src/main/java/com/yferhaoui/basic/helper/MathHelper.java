package com.yferhaoui.basic.helper;

import java.util.concurrent.ThreadLocalRandom;

public final class MathHelper {

	/* Random Methods */
	public final static long nextLong(final long nb) {
		if (nb > 0) {
			return ThreadLocalRandom.current().nextLong(nb);
		}
		return 0;
	}

	public final static long nextLong(final long min, final long max) {
		if ((max - min) > 0) {
			return ThreadLocalRandom.current().nextLong(Math.min(min, max), Math.max(min, max));
		}
		return 0;
	}

	public final static int nextInteger(final int nb) {
		if (nb > 0) {
			return ThreadLocalRandom.current().nextInt(nb);
		}
		return 0;
	}

	public final static int nextInteger(final int min, final int max) {
		if ((max - min) > 0) {
			return ThreadLocalRandom.current().nextInt(Math.min(min, max), Math.max(min, max));
		}
		return 0;
	}

	public final static double nextDouble(final double nb) {
		if (nb > 0.0) {
			return ThreadLocalRandom.current().nextDouble(nb);
		}
		return 0.0;
	}

	public final static double nextDouble(final double min, final double max) {
		if ((max - min) > 0.0) {
			return ThreadLocalRandom.current().nextDouble(Math.min(min, max), Math.max(min, max));
		}
		return 0.0;
	}

	public final static float nextFloat(final float nb) {
		return (float) MathHelper.nextDouble(nb);
	}

	public final static float nextDouble(final float min, final float max) {
		return (float) MathHelper.nextDouble(min, max);
	}

}
