import { mergeProps, withCtx, createVNode, createTextVNode, useSSRContext } from "vue";
import { ssrRenderAttrs, ssrRenderComponent, ssrRenderAttr, ssrIncludeBooleanAttr, ssrRenderSlot } from "vue/server-renderer";
import { _ as __nuxt_component_0 } from "./BaseContainer-CXozW73N.js";
import { _ as __nuxt_component_0$1 } from "./nuxt-link-pnhsZJTW.js";
import { publicAssetsURL } from "#internal/nuxt/paths";
import { _ as _export_sfc } from "../server.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/ufo/dist/index.mjs";
import "ofetch";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/hookable/dist/index.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/unctx/dist/index.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/h3/dist/index.mjs";
import "vue-router";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/radix3/dist/index.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/defu/dist/defu.mjs";
import "/home/manuk/Desktop/structures/website-nuxt/node_modules/@unhead/vue/dist/index.mjs";
const _imports_0 = publicAssetsURL("/icons/logo.svg");
const _imports_1 = publicAssetsURL("/icons/git.svg");
const _sfc_main$2 = {};
function _sfc_ssrRender$1(_ctx, _push, _parent, _attrs) {
  const _component_BaseContainer = __nuxt_component_0;
  const _component_NuxtLink = __nuxt_component_0$1;
  _push(`<header${ssrRenderAttrs(mergeProps({ class: "bg-white text-black dark:bg-[rgba(0,0,0,0.8)] dark:text-white shadow-md sticky top-0 left-0 z-50" }, _attrs))}>`);
  _push(ssrRenderComponent(_component_BaseContainer, null, {
    default: withCtx((_, _push2, _parent2, _scopeId) => {
      if (_push2) {
        _push2(`<nav class="mx-auto py-3 flex justify-between items-center px-[88px]"${_scopeId}>`);
        _push2(ssrRenderComponent(_component_NuxtLink, {
          to: "/",
          class: "text-lg font-bold text-blue-700 dark:text-orange-700"
        }, {
          default: withCtx((_2, _push3, _parent3, _scopeId2) => {
            if (_push3) {
              _push3(`<img${ssrRenderAttr("src", _imports_0)} class="invert-100 dark:invert-0" alt="logo"${_scopeId2}>`);
            } else {
              return [
                createVNode("img", {
                  src: _imports_0,
                  class: "invert-100 dark:invert-0",
                  alt: "logo"
                })
              ];
            }
          }),
          _: 1
        }, _parent2, _scopeId));
        _push2(`<div class="space-x-4 flex items-center"${_scopeId}>`);
        _push2(ssrRenderComponent(_component_NuxtLink, {
          to: "#",
          class: "text-gray-900 hover:text-gray-700 dark:text-gray-300"
        }, {
          default: withCtx((_2, _push3, _parent3, _scopeId2) => {
            if (_push3) {
              _push3(`Guide`);
            } else {
              return [
                createTextVNode("Guide")
              ];
            }
          }),
          _: 1
        }, _parent2, _scopeId));
        _push2(ssrRenderComponent(_component_NuxtLink, {
          to: "#",
          class: "text-gray-900 hover:text-gray-700 dark:text-gray-300"
        }, {
          default: withCtx((_2, _push3, _parent3, _scopeId2) => {
            if (_push3) {
              _push3(`Reference`);
            } else {
              return [
                createTextVNode("Reference")
              ];
            }
          }),
          _: 1
        }, _parent2, _scopeId));
        _push2(ssrRenderComponent(_component_NuxtLink, {
          to: "#",
          class: "text-gray-900 hover:text-gray-700 dark:text-gray-300"
        }, {
          default: withCtx((_2, _push3, _parent3, _scopeId2) => {
            if (_push3) {
              _push3(`Test Status`);
            } else {
              return [
                createTextVNode("Test Status")
              ];
            }
          }),
          _: 1
        }, _parent2, _scopeId));
        _push2(ssrRenderComponent(_component_NuxtLink, {
          to: "#",
          class: "text-gray-900 hover:text-gray-700 dark:text-gray-300 flex items-center gap-1"
        }, {
          default: withCtx((_2, _push3, _parent3, _scopeId2) => {
            if (_push3) {
              _push3(`<img${ssrRenderAttr("src", _imports_1)} alt="GitHub" class="w-5 h-5"${_scopeId2}> 80.2K `);
            } else {
              return [
                createVNode("img", {
                  src: _imports_1,
                  alt: "GitHub",
                  class: "w-5 h-5"
                }),
                createTextVNode(" 80.2K ")
              ];
            }
          }),
          _: 1
        }, _parent2, _scopeId));
        _push2(`<label class="relative inline-flex items-center cursor-pointer"${_scopeId}><input type="checkbox" class="sr-only peer"${ssrIncludeBooleanAttr(_ctx.$colorMode.preference === "dark") ? " checked" : ""}${_scopeId}><div class="w-11 h-6 bg-gray-300 peer-focus:outline-none peer-focus:ring-2 peer-focus:ring-blue-500 rounded-full peer dark:bg-gray-600 peer-checked:bg-blue-600 transition-all"${_scopeId}></div><div class="absolute left-0.5 top-0.5 w-5 h-5 bg-white rounded-full transition-transform peer-checked:translate-x-full"${_scopeId}></div></label></div></nav>`);
      } else {
        return [
          createVNode("nav", { class: "mx-auto py-3 flex justify-between items-center px-[88px]" }, [
            createVNode(_component_NuxtLink, {
              to: "/",
              class: "text-lg font-bold text-blue-700 dark:text-orange-700"
            }, {
              default: withCtx(() => [
                createVNode("img", {
                  src: _imports_0,
                  class: "invert-100 dark:invert-0",
                  alt: "logo"
                })
              ]),
              _: 1
            }),
            createVNode("div", { class: "space-x-4 flex items-center" }, [
              createVNode(_component_NuxtLink, {
                to: "#",
                class: "text-gray-900 hover:text-gray-700 dark:text-gray-300"
              }, {
                default: withCtx(() => [
                  createTextVNode("Guide")
                ]),
                _: 1
              }),
              createVNode(_component_NuxtLink, {
                to: "#",
                class: "text-gray-900 hover:text-gray-700 dark:text-gray-300"
              }, {
                default: withCtx(() => [
                  createTextVNode("Reference")
                ]),
                _: 1
              }),
              createVNode(_component_NuxtLink, {
                to: "#",
                class: "text-gray-900 hover:text-gray-700 dark:text-gray-300"
              }, {
                default: withCtx(() => [
                  createTextVNode("Test Status")
                ]),
                _: 1
              }),
              createVNode(_component_NuxtLink, {
                to: "#",
                class: "text-gray-900 hover:text-gray-700 dark:text-gray-300 flex items-center gap-1"
              }, {
                default: withCtx(() => [
                  createVNode("img", {
                    src: _imports_1,
                    alt: "GitHub",
                    class: "w-5 h-5"
                  }),
                  createTextVNode(" 80.2K ")
                ]),
                _: 1
              }),
              createVNode("label", { class: "relative inline-flex items-center cursor-pointer" }, [
                createVNode("input", {
                  type: "checkbox",
                  class: "sr-only peer",
                  checked: _ctx.$colorMode.preference === "dark",
                  onChange: ($event) => _ctx.$colorMode.preference = _ctx.$colorMode.preference === "dark" ? "light" : "dark"
                }, null, 40, ["checked", "onChange"]),
                createVNode("div", { class: "w-11 h-6 bg-gray-300 peer-focus:outline-none peer-focus:ring-2 peer-focus:ring-blue-500 rounded-full peer dark:bg-gray-600 peer-checked:bg-blue-600 transition-all" }),
                createVNode("div", { class: "absolute left-0.5 top-0.5 w-5 h-5 bg-white rounded-full transition-transform peer-checked:translate-x-full" })
              ])
            ])
          ])
        ];
      }
    }),
    _: 1
  }, _parent));
  _push(`</header>`);
}
const _sfc_setup$2 = _sfc_main$2.setup;
_sfc_main$2.setup = (props, ctx) => {
  const ssrContext = useSSRContext();
  (ssrContext.modules || (ssrContext.modules = /* @__PURE__ */ new Set())).add("components/Header.vue");
  return _sfc_setup$2 ? _sfc_setup$2(props, ctx) : void 0;
};
const Header = /* @__PURE__ */ _export_sfc(_sfc_main$2, [["ssrRender", _sfc_ssrRender$1]]);
const _sfc_main$1 = {};
function _sfc_ssrRender(_ctx, _push, _parent, _attrs) {
  const _component_BaseContainer = __nuxt_component_0;
  _push(`<footer${ssrRenderAttrs(mergeProps({ class: "bg-transparent text-sm dark:text-white/50 text-[#101010] py-[40px]" }, _attrs))}>`);
  _push(ssrRenderComponent(_component_BaseContainer, null, {
    default: withCtx((_, _push2, _parent2, _scopeId) => {
      if (_push2) {
        _push2(`<span class="px-[88px]"${_scopeId}> Copyright © 2018-present Kinotic Foundation </span>`);
      } else {
        return [
          createVNode("span", { class: "px-[88px]" }, " Copyright © 2018-present Kinotic Foundation ")
        ];
      }
    }),
    _: 1
  }, _parent));
  _push(`</footer>`);
}
const _sfc_setup$1 = _sfc_main$1.setup;
_sfc_main$1.setup = (props, ctx) => {
  const ssrContext = useSSRContext();
  (ssrContext.modules || (ssrContext.modules = /* @__PURE__ */ new Set())).add("components/Footer.vue");
  return _sfc_setup$1 ? _sfc_setup$1(props, ctx) : void 0;
};
const Footer = /* @__PURE__ */ _export_sfc(_sfc_main$1, [["ssrRender", _sfc_ssrRender]]);
const _sfc_main = {
  __name: "default",
  __ssrInlineRender: true,
  setup(__props) {
    return (_ctx, _push, _parent, _attrs) => {
      _push(`<div${ssrRenderAttrs(mergeProps({ class: "min-h-screen flex flex-col" }, _attrs))}>`);
      _push(ssrRenderComponent(Header, null, null, _parent));
      _push(`<main class="flex-1">`);
      ssrRenderSlot(_ctx.$slots, "default", {}, null, _push, _parent);
      _push(`</main>`);
      _push(ssrRenderComponent(Footer, null, null, _parent));
      _push(`</div>`);
    };
  }
};
const _sfc_setup = _sfc_main.setup;
_sfc_main.setup = (props, ctx) => {
  const ssrContext = useSSRContext();
  (ssrContext.modules || (ssrContext.modules = /* @__PURE__ */ new Set())).add("layouts/default.vue");
  return _sfc_setup ? _sfc_setup(props, ctx) : void 0;
};
export {
  _sfc_main as default
};
//# sourceMappingURL=default-BGeegPLo.js.map
