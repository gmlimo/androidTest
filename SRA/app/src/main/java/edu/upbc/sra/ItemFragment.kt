package edu.upbc.sra

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import edu.upbc.sra.databinding.FragmentItemListBinding
import edu.upbc.sra.utils.Option
import edu.upbc.sra.utils.RecyclerAdapter

private lateinit var recycler: RecyclerView

class ItemFragment : Fragment(), RecyclerAdapter.OnItemClickListener {
    private var fragmentBlankBinding: FragmentItemListBinding? = null

    private lateinit var items: List<Option>
    private lateinit var mAdapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentItemListBinding.bind(view)
        fragmentBlankBinding = binding

        recycler = binding.optionView
        items = listOf(
            Option(R.drawable.ais1, getString(R.string.automatic_irrigation_system)),
            Option(R.drawable.ais2, getString(R.string.catalog)),
            Option(R.drawable.ais3, getString(R.string.browse))
        )

        setUpRecyclerView(items)
    }

    private fun setUpRecyclerView(mItems: List<Option>) {
        recycler.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ItemFragment?.requireContext())
            mAdapter = RecyclerAdapter(mItems, this@ItemFragment)
            adapter = mAdapter
        }
    }

    override fun onItemClick(position: Int) {
        when (position) {
            0 -> findNavController().navigate(R.id.controllerFragment)
            1 -> Toast.makeText(this@ItemFragment?.requireContext(), "Catalogo", Toast.LENGTH_LONG).show()
            2 -> Toast.makeText(this@ItemFragment?.requireContext(), "Browser", Toast.LENGTH_LONG).show()
        }
    }
}