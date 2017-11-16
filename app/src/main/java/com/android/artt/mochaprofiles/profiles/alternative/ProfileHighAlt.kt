package com.android.artt.mochaprofiles.profiles.alternative

import com.android.artt.mochaprofiles.base.ProfileBase

class ProfileHighAlt : ProfileBase(), ProfileBase.intelliactiveGovernor {
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
    override val scheduler: String
        get() = deadline
    override val governor: String
        get() = intelliactive
    override val max_cpu_online: String
        get() = "4"
    override val min_cpu_online: String
        get() = "2"
    override val min_sample_time: String
        get() = "80000"
    override val timer_rate: String
        get() = "50000"
    override val sampling_down_factor: String
        get() = "2"
    override val above_hispeed_delay: String
        get() = "20000 828000:10000 1224000:20000"
    override val io_is_busy: String
        get() = "1"
    override val sync_freq: String
        get() = "828000"
    override val up_threshold_any_cpu_load: String
        get() = "55"
    override val up_threshold_any_cpu_freq: String
        get() = "1044000"
    override val hispeed_freq: String
        get() = "1428000"
    override val target_loads: String
        get() = "75"
}