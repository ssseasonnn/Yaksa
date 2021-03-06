package zlc.season.yasha

import android.view.LayoutInflater
import android.view.ViewGroup

class YashaItemDsl<T : YashaItem> {
    private var initScope: YashaScope<T>.() -> Unit = {}

    private var resId: Int = 0
    private var onBind: YashaScope<T>.() -> Unit = {}
    private var onBindPayload: YashaScope<T>.(payload: List<Any>) -> Unit = { _: List<Any> -> }

    private var onAttach: YashaScope<T>.() -> Unit = {}
    private var onDetach: YashaScope<T>.() -> Unit = {}
    private var onRecycled: YashaScope<T>.() -> Unit = {}

    private var gridSpanSize = 1
    private var staggerFullSpan = false

    fun initScope(block: YashaScope<T>.() -> Unit) {
        this.initScope = block
    }

    /**
     * Set item res layout resource
     */
    fun res(res: Int) {
        this.resId = res
    }

    fun onBind(block: YashaScope<T>.() -> Unit) {
        this.onBind = block
    }

    fun onBindPayload(block: YashaScope<T>.(payload: List<Any>) -> Unit) {
        this.onBindPayload = block
    }

    fun onAttach(block: YashaScope<T>.() -> Unit) {
        this.onAttach = block
    }

    fun onDetach(block: YashaScope<T>.() -> Unit) {
        this.onDetach = block
    }

    fun onRecycled(block: YashaScope<T>.() -> Unit) {
        this.onRecycled = block
    }

    /**
     * Only work for Grid, set the span size of this item
     *
     * @param spanSize spanSize
     */
    fun gridSpanSize(spanSize: Int) {
        this.gridSpanSize = spanSize
    }

    /**
     * Only work for Stagger, set the fullSpan of this item
     *
     * @param fullSpan True or false
     */
    fun staggerFullSpan(fullSpan: Boolean) {
        this.staggerFullSpan = fullSpan
    }

    fun prepare(type: Int, adapter: YashaAdapter) {
        adapter.registerItemBuilder(
                type, YashaItemBuilder(
                gridSpanSize,
                staggerFullSpan,
                ::builder)
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun builder(viewGroup: ViewGroup): YashaViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(this.resId, viewGroup, false)

        return object : YashaViewHolder(view) {
            val viewHolderScope = YashaScope<T>(view)

            init {
                viewHolderScope.initScope()
            }

            override fun onBind(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onBind()
                }
            }

            override fun onBindPayload(position: Int, t: YashaItem, payload: MutableList<Any>) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onBindPayload(payload)
                }
            }

            override fun onAttach(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onAttach()
                }
            }

            override fun onDetach(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onDetach()
                }
            }

            override fun onRecycled(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onRecycled()
                }
            }
        }
    }
}