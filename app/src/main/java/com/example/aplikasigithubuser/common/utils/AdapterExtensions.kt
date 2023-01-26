import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.aplikasigithubuser.common.base.BasePagingAdapter
import com.example.aplikasigithubuser.common.base.BaseViewHolder

inline fun <T : Any, B : ViewBinding, VH : BaseViewHolder<T, B>> setAdapter(
  diffUtil: DiffUtil.ItemCallback<T>,
  crossinline create: (parent: ViewGroup) -> VH
): BasePagingAdapter<T, VH> =
  BasePagingAdapter(
    diffUtil = diffUtil,
    onCreateViewHolder = { parent ->
      create.invoke(parent)
    },
    onBindViewHolder = { holder, position, item ->
      holder.bindItem(position, item!!)
    }
  )

private typealias ViewHolderViewBindingInflater<VB> = (
  inflater: LayoutInflater,
  parent: ViewGroup,
  attachToParent: Boolean
) -> VB

fun <VB : ViewBinding> ViewGroup.inflateBinding(
  bindingInflater: ViewHolderViewBindingInflater<VB>
): VB {
  return bindingInflater.invoke(LayoutInflater.from(context), this, false)
}