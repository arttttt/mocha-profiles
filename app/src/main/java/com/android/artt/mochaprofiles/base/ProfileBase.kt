@file:Suppress("LeakingThis")

package com.android.artt.mochaprofiles.base

abstract class ProfileBase {
    //common params
    protected abstract val scaling_max_freq: String
    protected abstract val scaling_min_freq: String
    protected abstract val gpu_floor_state: String
    protected abstract val gpu_floor_rate: String
    protected abstract val gpu_cap_state: String
    protected abstract val gpu_cap_rate: String
    protected abstract val boost_freq: String
    protected abstract val boost_time: String
    protected abstract val boost_emc: String
    protected abstract val boost_gpu: String
    protected abstract val boost_cpus: String
    protected abstract val scheduler: String
    protected abstract val governor: String
    protected abstract val max_cpu_online: String
    protected abstract val min_cpu_online: String

    //common params
    val MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq"
    val MIN_FREQ =  "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq"
    val GPU_FLOOR_STATE =  "/sys/kernel/tegra_gpu/gpu_floor_state"
    val GPU_FLOOR_RATE =  "/sys/kernel/tegra_gpu/gpu_floor_rate"
    val GPU_CAP_STATE =  "/sys/kernel/tegra_gpu/gpu_cap_state"
    val GPU_CAP_RATE =  "/sys/kernel/tegra_gpu/gpu_cap_rate"
    val BOOST_FREQ =  "/sys/module/input_cfboost/parameters/boost_freq"
    val BOOST_TIME =  "/sys/module/input_cfboost/parameters/boost_time"
    val BOOST_EMC =  "/sys/module/input_cfboost/parameters/boost_emc"
    val BOOST_GPU =  "/sys/module/input_cfboost/parameters/boost_gpu"
    val BOOST_CPUS =  "/sys/module/input_cfboost/parameters/boost_cpus"
    val INTERNAL_SCHEDULER =  "/sys/block/mmcblk0/queue/scheduler"
    val EXTERNAL_SCHEDULER =  "/sys/block/mmcblk1/queue/scheduler"
    val SCALING_GOVERNOR =  "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"
    val MAX_CPU_ONLINE =  "/sys/devices/system/cpu/cpuquiet/tegra_cpuquiet/custom_max_cpu_online"
    val MIN_CPU_ONLINE =  "/sys/devices/system/cpu/cpuquiet/tegra_cpuquiet/custom_min_cpu_online"

    val commonParams: Map<String, String>

    protected val deadline: String
        get() = "deadline"
    protected val noop: String
        get() = "noop"

    val smartmax: String
        get() = "smartmax"
    val interactive: String
        get() = "interactive"
    val intelliactive: String
        get() = "intelliactive"

    init {
        commonParams = mapOf(SCALING_GOVERNOR to governor,
                MAX_FREQ to scaling_max_freq,
                MIN_FREQ to scaling_min_freq,
                GPU_FLOOR_STATE to gpu_floor_state,
                GPU_FLOOR_RATE to gpu_floor_rate,
                GPU_CAP_STATE to gpu_cap_state,
                GPU_CAP_RATE to gpu_cap_rate,
                BOOST_FREQ to boost_freq,
                BOOST_TIME to boost_time,
                BOOST_EMC to boost_emc,
                BOOST_GPU to boost_gpu,
                BOOST_CPUS to boost_cpus,
                INTERNAL_SCHEDULER to scheduler,
                EXTERNAL_SCHEDULER to scheduler,
                MAX_CPU_ONLINE to max_cpu_online,
                MIN_CPU_ONLINE to min_cpu_online)
    }

    interface smartmaxGovernor {
        //Smartmax params
        val awake_ideal_freq: String
        val smartmax_boost_freq: String
        val touch_poke_freq: String
        val input_boost_duration: String

        //smartmax params
        val AWAKE_IDEAL_FREQ
            get() = "/sys/devices/system/cpu/cpufreq/smartmax/awake_ideal_freq"
        val SMARTMAX_BOOST_FREQ
            get() = "/sys/devices/system/cpu/cpufreq/smartmax/boost_freq"
        val TOUCH_POKE_FREQ
            get() = "/sys/devices/system/cpu/cpufreq/smartmax/touch_poke_freq"
        val INPUT_BOOST_DURATION
            get() = "/sys/devices/system/cpu/cpufreq/smartmax/input_boost_duration"

        val smartmaxParams: Map<String, String>
            get() = mapOf(AWAKE_IDEAL_FREQ to awake_ideal_freq,
                    SMARTMAX_BOOST_FREQ to smartmax_boost_freq,
                    TOUCH_POKE_FREQ to touch_poke_freq,
                    INPUT_BOOST_DURATION to input_boost_duration)
    }

    interface interactiveGovernor {
        //Interactive params
        val target_loads: String
        val hispeed_freq: String
        val go_hispeed_load: String
        val timer_rate: String
        val timer_slack: String
        val io_busy_threshold: String
        val io_is_busy: String
        val min_sample_time: String
        val above_hispeed_delay: String

        //interactive params
        val TARGET_LOADS
            get() = "/sys/devices/system/cpu/cpufreq/interactive/target_loads"
        val HISPEED_FREQ
            get() = "/sys/devices/system/cpu/cpufreq/interactive/hispeed_freq"
        val GO_HISPEED_LOAD
            get() = "/sys/devices/system/cpu/cpufreq/interactive/go_hispeed_load"
        val TIMER_RATE
            get() = "/sys/devices/system/cpu/cpufreq/interactive/timer_rate"
        val TIMER_SLACK
            get() = "/sys/devices/system/cpu/cpufreq/interactive/timer_slack"
        val IO_BUSY_THRESHOLD
            get() = "/sys/devices/system/cpu/cpufreq/interactive/io_busy_threshold"
        val IO_IS_BUSY
            get() = "/sys/devices/system/cpu/cpufreq/interactive/io_is_busy"
        val MIN_SAMPLE_TIME
            get() = "/sys/devices/system/cpu/cpufreq/interactive/min_sample_time"
        val ABOVE_HISPEED_DELAY
            get() = "/sys/devices/system/cpu/cpufreq/interactive/above_hispeed_delay"

        val interactiveParams: Map<String, String>
            get() = mapOf(TARGET_LOADS to target_loads,
                    HISPEED_FREQ to hispeed_freq,
                    GO_HISPEED_LOAD to go_hispeed_load,
                    TIMER_RATE to timer_rate,
                    TIMER_SLACK to timer_slack,
                    IO_BUSY_THRESHOLD to io_busy_threshold,
                    IO_IS_BUSY to io_is_busy,
                    MIN_SAMPLE_TIME to min_sample_time,
                    ABOVE_HISPEED_DELAY to above_hispeed_delay)
    }

    interface intelliactiveGovernor {
        val min_sample_time: String
        val timer_rate: String
        val sampling_down_factor: String
        val above_hispeed_delay: String
        val io_is_busy: String
        val sync_freq: String
        val up_threshold_any_cpu_load: String
        val up_threshold_any_cpu_freq: String
        val hispeed_freq: String
        val target_loads: String

        val MIN_SAMPLE_TIME
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/min_sample_time"
        val TIMER_RATE
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/timer_rate"
        val SAMPLING_DOWN_FACTOR
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/sampling_down_factor"
        val ABOVE_HISPEED_DELAY
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/above_hispeed_delay"
        val IO_IS_BUSY
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/io_is_busy"
        val SYNC_FREQ
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/sync_freq"
        val UP_THRESHOLD_ANY_CPU_LOAD
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/up_threshold_any_cpu_load"
        val UP_THRESHOLD_ANY_CPU_FREQ
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/up_threshold_any_cpu_freq"
        val HISPEED_FREQ
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/hispeed_freq"
        val TARGET_LOADS
            get() = "/sys/devices/system/cpu/cpufreq/intelliactive/target_loads"

        val intelliactiveParams: Map<String, String>
            get() = mapOf(MIN_SAMPLE_TIME to min_sample_time,
                    TIMER_RATE to timer_rate,
                    SAMPLING_DOWN_FACTOR to sampling_down_factor,
                    ABOVE_HISPEED_DELAY to above_hispeed_delay,
                    IO_IS_BUSY to io_is_busy,
                    SYNC_FREQ to sync_freq,
                    UP_THRESHOLD_ANY_CPU_LOAD to up_threshold_any_cpu_load,
                    UP_THRESHOLD_ANY_CPU_FREQ to up_threshold_any_cpu_freq,
                    HISPEED_FREQ to hispeed_freq,
                    TARGET_LOADS to target_loads)
    }
}