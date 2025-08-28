<script lang="ts">
import { Component, Vue, Prop, Ref, Watch } from "vue-facing-decorator";
import CrudTable from "@/components/CrudTable.vue";
import StructureDataViewModal from "@/components/modals/StructureDataViewModal.vue";
import StructureItemModal from "@/components/modals/StructureItemModal.vue";
import type {
  Identifiable,
  IterablePage,
  Pageable,
} from "@kinotic/continuum-client";
import { APPLICATION_STATE } from "@/states/IApplicationState";
import { Structure, Structures, type IStructureService } from "@kinotic/structures-api";
import type { CrudHeader } from "@/types/CrudHeader";
import DatetimeUtil from "@/util/DatetimeUtil";

@Component({
  components: { CrudTable, StructureDataViewModal, StructureItemModal },
})
export default class StructuresList extends Vue {
  @Prop({ required: true }) applicationId!: string;
  @Prop({ required: false, default: undefined }) projectId?: string;
  @Prop({ required: false, default: "" }) initialSearch!: string;

  @Ref("crudTable") crudTable!: InstanceType<typeof CrudTable>;

  structureTableHeaders: CrudHeader[] = [
    { field: "name", header: "Structure name", sortable: true },
    { field: "projectId", header: "Project", sortable: true },
    { field: "description", header: "Description", sortable: false },
    { field: "created", header: "Created", sortable: false },
    { field: "updated", header: "Updated", sortable: false },
  { field: "published", header: "Status", sortable: false, centered: true },

  ];

  showModal = false;
  showItemModal = false;
  selectedStructure: Structure | null = null;
  searchText = "";
  isInitialized = false;

  actionMenus: any[] = [];
  currentActionItem: Structure | null = null;

  mounted(): void {
    this.searchText = this.initialSearch || "";
    this.isInitialized = true;
  }

  @Watch("initialSearch")
  onInitialSearchChanged(newVal: string) {
    if (this.isInitialized) {
      this.searchText = newVal || "";
      this.refreshTable();
    }
  }

  @Watch("applicationId")
  onApplicationIdChanged() {
    this.refreshTable();
  }

  @Watch("projectId")
  onProjectIdChanged() {
    this.refreshTable();
  }

  private dataSource1: IStructureService = Structures.getStructureService();

  get dataSource() {
    return {
      findAll: async (pageable: Pageable): Promise<IterablePage<Structure>> => {
        const service = Structures.getStructureService();
        const result = this.projectId
          ? await service.findAllForProject(this.projectId, pageable)
          : await service.findAllForApplication(this.applicationId, pageable);

        APPLICATION_STATE.structuresCount = result.totalElements ?? 0;
        return result;
      },
      search: async (
        _searchText: string,
        pageable: Pageable
      ): Promise<IterablePage<Structure>> => {
        const filter = this.projectId
          ? `projectId:${this.projectId}`
          : `applicationId:${this.applicationId}`;
        const query = `${filter} && ${this.searchText}`;
        return Structures.getStructureService().search(query, pageable);
      },
    };
  }

  get structuresCount() {
    return APPLICATION_STATE.structuresCount;
  }

  public DatetimeUtil = DatetimeUtil;

  refreshTable(): void {
    if (this.crudTable?.find) {
      this.crudTable.find();
    }
  }

  updateRouteQuery(newSearch: string): void {
    this.searchText = newSearch;
    const query = { ...this.$route.query };

    if (newSearch) {
      query["search-structure"] = newSearch;
    } else {
      delete query["search-structure"];
    }

    this.$router.replace({ query }).catch(() => {});
    this.refreshTable();
  }

  openModal(item: Structure) {
    this.selectedStructure = item;
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
    this.selectedStructure = null;
  }

  openItemModal(item: Structure) {
    this.selectedStructure = item;
    this.showItemModal = true;
  }

  closeItemModal() {
    this.showItemModal = false;
    this.selectedStructure = null;
  }

  handleRowClick(item: Structure): void {
    this.openModal(item);
  }

  onEditItem(item: Identifiable<string>): void {
    this.$router.push(`${this.$route.path}/edit/${item.id}`);
  }

  toggleMenu(event: Event, item: Structure, index: number) {
    this.currentActionItem = item;
    const menu = this.actionMenus[index];
    if (menu) {
      menu.toggle(event);
    }
  }

  public async publish(item: any) {
    item["publishing"] = true;
    if (confirm("Are you sure you want to Publish this Structure?")) {
      let table: any = this.$refs?.crudTable;
      try {
        await this.dataSource1.publish(item.id);
        table?.find();
        delete item["publishing"];
      } catch (error: any) {
        delete item["publishing"];
        table?.displayAlert(error.message);
      }
    }
  }

  public async unPublish(item: any) {
    item["publishing"] = true;
    if (
      confirm(
        "Are you sure you want to Remove Published Status for this Structure? \nAll data saved under this Structure will be permanently deleted, proceed with caution."
      )
    ) {
      let table: any = this.$refs?.crudTable;
      try {
        await this.dataSource1.unPublish(item.id);
        table?.find();
        delete item["publishing"];
      } catch (error: any) {
        delete item["publishing"];
        table?.displayAlert(error.message);
      }
    }
  }

  getActionMenu(item: Structure) {
    return [
      {
        label: item.published ? "Unpublish" : "Publish",
        icon: item.published ? "pi pi-eye-slash" : "pi pi-eye",
        command: () =>
          item.published ? this.unPublish(item) : this.publish(item),
      },
      {
        label: "View",
        icon: "pi pi-file",
        command: (e: any) => {
          e?.originalEvent?.stopPropagation?.();
          e?.originalEvent?.preventDefault?.();
          this.openItemModal(item);
        }
      },
    ];
  }
}
</script>

<template>
  <div>
    <CrudTable
      ref="crudTable"
      rowHoverColor=""
      :data-source="dataSource"
      :headers="structureTableHeaders"
      :singleExpand="false"
      :search="searchText"
      @update:search="updateRouteQuery"
      @edit-item="onEditItem"
      @onRowClick="handleRowClick"
      createNewButtonText="New Structure"
      class="!text-sm"
      emptyStateText="No structures yet"
    >
      <template #item.created="{ item }">
        <span>{{ DatetimeUtil.formatMonthDayYear(item.created) }}</span>
      </template>

      <template #item.updated="{ item }">
        <span>{{ DatetimeUtil.formatRelativeDate(item.updated) }}</span>
      </template>

      <template #item.published="{ item }">
        <div class="w-full text-center">
          <Tag
            :value="item.published ? 'Published' : 'Unpublished'"
            :severity="item.published ? 'success' : 'secondary'"
            class="px-2 py-1 text-sm"
            rounded
          />
        </div>
      </template>

      <template #additional-actions="{ item }">
        <div class="flex items-center justify-center">
          <Button
            icon="pi pi-ellipsis-v"
            @click.stop="(event) => toggleMenu(event, item, item.id)"
            aria-haspopup="true"
            :aria-controls="'action_menu_' + item.id"
            type="button"
            severity="secondary"
            variant="text"
          />
          <Menu
            :ref="(el) => (actionMenus[item.id] = el)"
            :model="getActionMenu(item)"
            :popup="true"
            :id="'action_menu_' + item.id"
          />
        </div>
      </template>
    </CrudTable>

    <StructureDataViewModal
      v-if="selectedStructure"
      v-model="showModal"
      :title="selectedStructure?.name || 'Data View'"
      :entity-props="{ structureId: selectedStructure?.id }"
      @close="closeModal"
    />

    <StructureItemModal
      v-if="showItemModal && selectedStructure"
      :item="selectedStructure"
      @close="closeItemModal"
    />
  </div>
</template>