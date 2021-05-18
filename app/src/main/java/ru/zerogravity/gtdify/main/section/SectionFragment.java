package ru.zerogravity.gtdify.main.section;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.zerogravity.gtdify.R;
import ru.zerogravity.gtdify.databinding.FragmentSectionBinding;
import ru.zerogravity.gtdify.databinding.ItemCardBinding;
import ru.zerogravity.gtdify.model.Card;
import ru.zerogravity.gtdify.model.Project;
import ru.zerogravity.gtdify.model.Section;

public class SectionFragment extends Fragment {
    private Section section;

    private FragmentSectionBinding binding;
    private SectionViewModel viewModel;

    // TODO make newInstance
    public SectionFragment(Section section) {
        this.section = section;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentSectionBinding.inflate(inflater, container, false);
        viewModel = new SectionViewModel(section, requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupCardsRecycler();
    }

    private void setupCardsRecycler() {
        CardRecyclerViewAdapter adapter = new CardRecyclerViewAdapter(viewModel.getMutableCardsData().getValue());
        binding.cardsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cardsRecycler.setAdapter(adapter);

        viewModel.getMutableCardsData().observe(getViewLifecycleOwner(), (List<Card> cards) -> {
            adapter.setCards(cards);
            adapter.notifyDataSetChanged();
        });
    }

    private static class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.CardViewHolder> {

        private List<Card> cards;

        public CardRecyclerViewAdapter(List<Card> cards) {
            this.cards = cards;
        }

        public void setCards(List<Card> cards) {
            this.cards = cards;
        }

        @NonNull
        @NotNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new CardViewHolder(inflater.inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull CardViewHolder holder, int position) {
            holder.setup(cards.get(position));
        }

        @Override
        public int getItemCount() {
            return cards.size();
        }

        private static class CardViewHolder extends RecyclerView.ViewHolder {
            private ItemCardBinding binding;

            public CardViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                binding = ItemCardBinding.bind(itemView);
            }

            public void setup(Card card) {
                binding.cardCheckbox.setText(card.getName());
                // TODO priority
                // TODO date
                binding.cardCheckbox.setChecked(card.isComplete());
            }
        }
    }
}
