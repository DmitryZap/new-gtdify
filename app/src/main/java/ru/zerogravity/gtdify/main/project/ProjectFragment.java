package ru.zerogravity.gtdify.main.project;

import android.content.Intent;
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
import ru.zerogravity.gtdify.card.CardActivity;
import ru.zerogravity.gtdify.databinding.FragmentProjectBinding;
import ru.zerogravity.gtdify.main.section.SectionFragment;
import ru.zerogravity.gtdify.model.Project;
import ru.zerogravity.gtdify.model.Section;

public class ProjectFragment extends Fragment {
    private Project project;

    private FragmentProjectBinding binding;
    private ProjectViewModel viewModel;

    // TODO make newInstance
    public ProjectFragment(Project project) {
        this.project = project;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FragmentProjectBinding.inflate(inflater, container, false);
        viewModel = new ProjectViewModel(project, requireActivity().getApplication());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSectionsRecycler();
        setupAddCardButton();
    }

    private void setupSectionsRecycler() {
        SectionsRecyclerViewAdapter adapter = new SectionsRecyclerViewAdapter(viewModel.getMutableSectionsData().getValue());
        binding.sectionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.sectionsRecycler.setAdapter(adapter);

        viewModel.getMutableSectionsData().observe(getViewLifecycleOwner(), (List<Section> sections) -> {
            adapter.setSections(sections);
            adapter.notifyDataSetChanged();
        });
    }

    private void setupAddCardButton() {
        binding.addCardButton.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), CardActivity.class));
        });
    }

    private class SectionsRecyclerViewAdapter extends RecyclerView.Adapter<SectionsRecyclerViewAdapter.SectionViewHolder> {

        private List<Section> sections;

        public SectionsRecyclerViewAdapter(List<Section> sections) {
            this.sections = sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }

        @NonNull
        @NotNull
        @Override
        public SectionsRecyclerViewAdapter.SectionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new SectionsRecyclerViewAdapter.SectionViewHolder(inflater.inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull SectionsRecyclerViewAdapter.SectionViewHolder holder, int position) {
            holder.setup(sections.get(position));
        }

        @Override
        public int getItemCount() {
            return sections.size();
        }

        private class SectionViewHolder extends RecyclerView.ViewHolder {

            public SectionViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
            }

            public void setup(Section section) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.section_root, new SectionFragment(section))
                        .commit();
            }
        }
    }
}
