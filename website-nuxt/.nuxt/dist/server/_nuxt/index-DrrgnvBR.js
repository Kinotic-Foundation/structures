import { ssrRenderComponent, ssrRenderList, ssrRenderAttr, ssrInterpolate, ssrRenderAttrs } from "vue/server-renderer";
import { withCtx, createVNode, createBlock, openBlock, Fragment, renderList, toDisplayString, useSSRContext, mergeProps } from "vue";
import { _ as __nuxt_component_0 } from "./BaseContainer-CXozW73N.js";
import { publicAssetsURL } from "#internal/nuxt/paths";
import { _ as _export_sfc } from "../server.mjs";
import "ofetch";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/hookable/dist/index.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/unctx/dist/index.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/h3/dist/index.mjs";
import "vue-router";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/radix3/dist/index.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/defu/dist/defu.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/ufo/dist/index.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/@unhead/vue/dist/index.mjs";
const _sfc_main$2 = {
  __name: "FeaturesComponent",
  __ssrInlineRender: true,
  setup(__props) {
    const getIconPath = (filename) => `/icons/${filename}`;
    const features = [
      {
        title: "Schema Flexibility",
        details: "Structures makes schema evolution easy—letting developers adjust data schemas over time without major changes to application code. This streamlines the management of complex data and helps teams adapt their data models as business needs evolve.",
        icon: "share.svg"
      },
      {
        title: "Data Management",
        details: "Structures equips developers with powerful tools to create, read, update, and delete data—making it easy to manage complex datasets and offering a flexible interface for data manipulation.",
        icon: "database.svg"
      },
      {
        title: "Intuitive GUI and OpenAPI",
        details: "Structures offers an intuitive GUI built with Continuum and an OpenAPI interface, making it easy for developers to manage data and integrate Structures into existing applications and workflows.",
        icon: "folder.svg"
      }
    ];
    return (_ctx, _push, _parent, _attrs) => {
      _push(ssrRenderComponent(__nuxt_component_0, _attrs, {
        default: withCtx((_, _push2, _parent2, _scopeId) => {
          if (_push2) {
            _push2(`<section class="pt-16 px-[88px]"${_scopeId}><div class="flex flex-wrap border-b gap-[43px] border-white/20 justify-between pb-[70px] mx-auto"${_scopeId}><!--[-->`);
            ssrRenderList(features, (f, i) => {
              _push2(`<div class="flex-1 min-w-[250px] border border-black dark:border-white/20 bg-transparent p-8 rounded-xl"${_scopeId}><div class="flex items-center gap-5 mb-6"${_scopeId}><img${ssrRenderAttr("src", getIconPath(f.icon))} class="w-10 h-10" alt="icon"${_scopeId}><h3 class="text-xl font-semibold dark:text-white text-[#101010]"${_scopeId}>${ssrInterpolate(f.title)}</h3></div><p class="dark:text-white text-[#101010]"${_scopeId}>${ssrInterpolate(f.details)}</p></div>`);
            });
            _push2(`<!--]--></div></section>`);
          } else {
            return [
              createVNode("section", { class: "pt-16 px-[88px]" }, [
                createVNode("div", { class: "flex flex-wrap border-b gap-[43px] border-white/20 justify-between pb-[70px] mx-auto" }, [
                  (openBlock(), createBlock(Fragment, null, renderList(features, (f, i) => {
                    return createVNode("div", {
                      key: i,
                      class: "flex-1 min-w-[250px] border border-black dark:border-white/20 bg-transparent p-8 rounded-xl"
                    }, [
                      createVNode("div", { class: "flex items-center gap-5 mb-6" }, [
                        createVNode("img", {
                          src: getIconPath(f.icon),
                          class: "w-10 h-10",
                          alt: "icon"
                        }, null, 8, ["src"]),
                        createVNode("h3", { class: "text-xl font-semibold dark:text-white text-[#101010]" }, toDisplayString(f.title), 1)
                      ]),
                      createVNode("p", { class: "dark:text-white text-[#101010]" }, toDisplayString(f.details), 1)
                    ]);
                  }), 64))
                ])
              ])
            ];
          }
        }),
        _: 1
      }, _parent));
    };
  }
};
const _sfc_setup$2 = _sfc_main$2.setup;
_sfc_main$2.setup = (props, ctx) => {
  const ssrContext = useSSRContext();
  (ssrContext.modules || (ssrContext.modules = /* @__PURE__ */ new Set())).add("components/home/FeaturesComponent.vue");
  return _sfc_setup$2 ? _sfc_setup$2(props, ctx) : void 0;
};
const _imports_0 = publicAssetsURL("/images/hero-section-image.png");
const _sfc_main$1 = {};
function _sfc_ssrRender(_ctx, _push, _parent, _attrs) {
  const _component_BaseContainer = __nuxt_component_0;
  _push(`<div${ssrRenderAttrs(mergeProps({
    class: "w-full bg-cover bg-center bg-no-repeat py-1 px-4",
    style: { "background-image": "linear-gradient(rgba(0, 0, 0, 0.8), rgba(32, 43, 113, 0.28)), url('/images/background-home.png')" }
  }, _attrs))}>`);
  _push(ssrRenderComponent(_component_BaseContainer, null, {
    default: withCtx((_, _push2, _parent2, _scopeId) => {
      if (_push2) {
        _push2(`<div class="w-full flex justify-center items-center gap-[120px]"${_scopeId}><div class="max-w-[550px] mt-16 text-white"${_scopeId}><p class="flex flex-col mb-10 leading-[65px]"${_scopeId}><span class="text-[81px] font-normal"${_scopeId}> Data Evolution </span><span class="text-[66px] font-light text-[#C8FB39]"${_scopeId}> Made Simple </span></p><p class="text-[19px] text-white"${_scopeId}> Empower your teams to adapt fast—Structures supports safe schema changes, full CRUD operations, and effortless integration through a sleek GUI and OpenAPI. </p><div class="mt-10 flex gap-6"${_scopeId}><button class="px-4 py-2 rounded-lg bg-[#3651ED] text-white font-medium"${_scopeId}> Start your project </button><button class="px-4 py-2 rounded-lg bg-white text-slate-500 font-medium"${_scopeId}> Request for demo </button></div></div><div${_scopeId}><img${ssrRenderAttr("src", _imports_0)} alt="Hero Section Image" class="max-w-[700px]"${_scopeId}></div></div>`);
      } else {
        return [
          createVNode("div", { class: "w-full flex justify-center items-center gap-[120px]" }, [
            createVNode("div", { class: "max-w-[550px] mt-16 text-white" }, [
              createVNode("p", { class: "flex flex-col mb-10 leading-[65px]" }, [
                createVNode("span", { class: "text-[81px] font-normal" }, " Data Evolution "),
                createVNode("span", { class: "text-[66px] font-light text-[#C8FB39]" }, " Made Simple ")
              ]),
              createVNode("p", { class: "text-[19px] text-white" }, " Empower your teams to adapt fast—Structures supports safe schema changes, full CRUD operations, and effortless integration through a sleek GUI and OpenAPI. "),
              createVNode("div", { class: "mt-10 flex gap-6" }, [
                createVNode("button", { class: "px-4 py-2 rounded-lg bg-[#3651ED] text-white font-medium" }, " Start your project "),
                createVNode("button", { class: "px-4 py-2 rounded-lg bg-white text-slate-500 font-medium" }, " Request for demo ")
              ])
            ]),
            createVNode("div", null, [
              createVNode("img", {
                src: _imports_0,
                alt: "Hero Section Image",
                class: "max-w-[700px]"
              })
            ])
          ])
        ];
      }
    }),
    _: 1
  }, _parent));
  _push(`</div>`);
}
const _sfc_setup$1 = _sfc_main$1.setup;
_sfc_main$1.setup = (props, ctx) => {
  const ssrContext = useSSRContext();
  (ssrContext.modules || (ssrContext.modules = /* @__PURE__ */ new Set())).add("components/home/HeroComponent.vue");
  return _sfc_setup$1 ? _sfc_setup$1(props, ctx) : void 0;
};
const HeroComponent = /* @__PURE__ */ _export_sfc(_sfc_main$1, [["ssrRender", _sfc_ssrRender]]);
const _sfc_main = {
  __name: "index",
  __ssrInlineRender: true,
  setup(__props) {
    return (_ctx, _push, _parent, _attrs) => {
      _push(`<!--[-->`);
      _push(ssrRenderComponent(HeroComponent, null, null, _parent));
      _push(ssrRenderComponent(_sfc_main$2, null, null, _parent));
      _push(`<!--]-->`);
    };
  }
};
const _sfc_setup = _sfc_main.setup;
_sfc_main.setup = (props, ctx) => {
  const ssrContext = useSSRContext();
  (ssrContext.modules || (ssrContext.modules = /* @__PURE__ */ new Set())).add("pages/index.vue");
  return _sfc_setup ? _sfc_setup(props, ctx) : void 0;
};
export {
  _sfc_main as default
};
//# sourceMappingURL=index-DrrgnvBR.js.map
