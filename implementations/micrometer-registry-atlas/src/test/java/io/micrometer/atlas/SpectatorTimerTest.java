/**
 * Copyright 2017 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.atlas;

import com.netflix.spectator.atlas.AtlasConfig;
import io.micrometer.core.instrument.MockClock;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleConfig;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.micrometer.core.instrument.MockClock.clock;
import static org.assertj.core.api.Assertions.assertThat;

class SpectatorTimerTest {
    @Test
    void timerMax() {
        AtlasMeterRegistry registry = new AtlasMeterRegistry(new AtlasConfig() {
            @Override
            public String get(String k) {
                return null;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(10);
            }
        }, new MockClock());
        Timer timer = registry.timer("timer");

        timer.record(1, TimeUnit.SECONDS);
        clock(registry).add(SimpleConfig.DEFAULT_STEP);
        assertThat(timer.max(TimeUnit.MILLISECONDS)).isEqualTo(1000);
    }
}