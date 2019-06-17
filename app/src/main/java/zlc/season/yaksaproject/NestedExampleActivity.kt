//package zlc.season.yaksaproject
//
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.support.v7.widget.RecyclerView.HORIZONTAL
//import android.view.View
//import kotlinx.android.synthetic.main.activity_example.*
//import kotlinx.android.synthetic.main.error_item.view.*
//import kotlinx.android.synthetic.main.header_item.view.*
//import kotlinx.android.synthetic.main.list_item.view.*
//import kotlinx.android.synthetic.main.nested_header_item.view.*
//import kotlinx.android.synthetic.main.nested_header_layout.view.*
//import zlc.season.yasha.YaksaItem
//import zlc.season.yasha.linear
//import zlc.season.yaksaproject.ExampleViewModel.State
//
//class NestedExampleActivity : ExampleActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        example_rv.linear {
//
//            viewModel.headerData.observeX {
//                renderHeadersByDsl(it, clear = true) { headerData ->
//                    res(R.layout.header_item)
//                    render { view ->
//                        view.header_item_tv.text = headerData.header
//                    }
//                }
//                renderHeaders(mutableListOf(mutableListOf(
//                        "nested header1",
//                        "nested header2",
//                        "nested header3",
//                        "nested header4",
//                        "nested header5"
//                ))) {
//                    NestedHeaderItem(it)
//                }
//            }
//
//            viewModel.footerData.observeX {
//                renderFootersByDsl(it, clear = true) { footerData ->
//                    res(R.layout.header_item)
//                    render { view ->
//                        view.header_item_tv.text = footerData.footer
//                        view.setOnClickListener { }
//                    }
//                }
//            }
//
//            viewModel.itemData.observeX {
//                renderItemsByDsl(it.data, clear = it.isRefresh) { item ->
//                    res(R.layout.list_item)
//                    render { view ->
//                        view.list_item_tv.text = item.title
//                        view.setOnClickListener { toast("Item Clicked") }
//                    }
//                }
//            }
//
//            viewModel.state.observeX {
//                when (it) {
//                    is State.Loading -> {
//                        /**
//                         * Render a LOAD_MORE item that triggers load more action when it is displayed on the screen
//                         */
//                        renderStateItemByDsl("loading") {
//                            res(R.layout.loading_item)
//                            onItemAttachWindow {
//                                viewModel.loadData()
//                            }
//                        }
//                    }
//
//                    is State.Empty ->
//                        /**
//                         * render a NO_MORE state item
//                         */
//                        renderStateItemByDsl("empty") {
//                            res(R.layout.empty_item)
//                        }
//
//                    is State.Error ->
//                        /**
//                         * render an ERROR state item
//                         */
//                        renderStateItemByDsl("error") {
//                            res(R.layout.error_item)
//                            render {
//                                it.retry.setOnClickListener {
//                                    viewModel.loadData()
//                                }
//                            }
//                        }
//                }
//            }
//        }
//    }
//
//    private class NestedHeaderItem(val data: List<String>) : YaksaItem {
//        var scrollState = ScrollState(0, 0)
//
//        override fun res(): Int {
//            return R.layout.nested_header_layout
//        }
//
//        override fun render(position: Int, view: View) {
//
//            view.nested_header_rv.linear {
//                orientation(HORIZONTAL)
//
//                renderItemsByDsl(data) { item ->
//                    res(R.layout.nested_header_item)
//
//                    render { view ->
//                        view.nested_header_item_tv.text = item
//                        view.setOnClickListener { toast(view, "Item Clicked") }
//                    }
//                }
//            }
//        }
//
//        override fun onItemAttachWindow(position: Int, view: View) {
//            super.onItemAttachWindow(position, view)
//            resetScrollState(view.nested_header_rv, scrollState)
//        }
//
//        override fun onItemDetachWindow(position: Int, view: View) {
//            super.onItemDetachWindow(position, view)
//            scrollState = saveScrollState(view.nested_header_rv)
//        }
//
//
//        private fun saveScrollState(recyclerView: RecyclerView): ScrollState {
//            var offset = 0
//            var position = 0
//
//            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//            val topView = layoutManager.getChildAt(0)
//            if (topView != null) {
//                offset = topView.top
//                position = layoutManager.getPosition(topView)
//            }
//            return ScrollState(offset, position)
//        }
//
//
//        private fun resetScrollState(recyclerView: RecyclerView, scrollState: ScrollState) {
//            val (offset, position) = scrollState
//            if (recyclerView.layoutManager != null && position >= 0) {
//                (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, offset)
//            }
//        }
//    }
//
//    data class ScrollState(
//            val offset: Int,
//            val position: Int
//    )
//}