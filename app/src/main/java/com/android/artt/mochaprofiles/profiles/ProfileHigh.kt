package com.android.artt.mochaprofiles.profiles

class ProfileHigh : ProfileBase(), ProfileBase.interactiveGovernor {
    override val scaling_max_freq: String
        get() = "1836000"
    override val scaling_min_freq: String
        get() = "696000"
    override val gpu_floor_state: String
        get() = "1"
    override val gpu_floor_rate: String
        get() = "396000000"
    override val gpu_cap_state: String
        get() = "1"
    override val gpu_cap_rate: String
        get() = "852000000"
    override val boost_freq: String
        get() = "1044000"
    override val boost_time: String
        get() = "100"
    override val boost_emc: String
        get() = "0"
    override val boost_gpu: String
        get() = "396000"
    override val boost_cpus: String
        get() = "1"
    override val current_power_profile: String
        get() = "3"
    override val scheduler: String
        get() = deadline
    override val governor: String
        get() = interactive
    override val max_cpu_online: String
        get() = "4"
    override val min_cpu_online: String
        get() = "2"
    override val target_loads: String
        get() = "80"
    override val hispeed_freq: String
        get() = "1044000"
    override val go_hispeed_load: String
        get() = "99"
    override val timer_rate: String
        get() = "30000"
    override val timer_slack: String
        get() = "60000"
    override val io_busy_threshold: String
        get() = "100"
    override val io_is_busy: String
        get() = "1"
    override val min_sample_time: String
        get() = "60000"
    override val above_hispeed_delay: String
        get() = "10000 204000:5000 696000:10000"
}