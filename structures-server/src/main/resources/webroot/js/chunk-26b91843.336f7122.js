(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-26b91843"],{"943e":function(e,t,a){"use strict";a.d(t,"a",(function(){return s}));var i=a("3835"),r=a("d4ec"),n=a("bee2"),s=(a("a9e3"),function(){function DatetimeUtil(){Object(r["a"])(this,DatetimeUtil)}return Object(n["a"])(DatetimeUtil,null,[{key:"formatDateFromEpoch",value:function formatDateFromEpoch(e){var t="";if(0!==e){var a=new Date(Number(e)).toLocaleString("en-US",{hour12:!1}).split(", "),r=Object(i["a"])(a,2),n=r[0],s=r[1];t=n+" "+s}return t}},{key:"formatDate",value:function formatDate(e){var t="";if((null===e||void 0===e?void 0:e.length)>0){var a=new Date(e).toLocaleString("en-US",{hour12:!1}).split(", "),r=Object(i["a"])(a,2),n=r[0],s=r[1];t=n+" "+s}return t}}]),DatetimeUtil}())},cdb4:function(e,t,a){"use strict";a.r(t);var i=a("8336"),r=a("62ad"),n=a("a523"),s=a("132d"),u=a("0fd9"),l=function render(){var e=this,t=e._self._c;e._self._setupProxy;return t(n["a"],{staticClass:"no-gutters",attrs:{"fill-height":"",fluid:""}},[t(u["a"],{staticClass:"no-gutters fill-height"},[t(r["a"],[t("CrudTable",{ref:"crudTable",attrs:{"data-source":e.dataSource,headers:e.headers,singleExpand:!0,"disable-modifications":!0},on:{"add-item":e.onAddItem,"edit-item":e.onEditItem},scopedSlots:e._u([{key:"item.namespace",fn:function fn(a){var i=a.item;return[t("span",[e._v(e._s(i.namespace))])]}},{key:"item.created",fn:function fn(t){var a=t.item;return[e._v(" "+e._s(e.DatetimeUtil.formatDate(a.created))+" ")]}},{key:"item.updated",fn:function fn(t){var a=t.item;return[e._v(" "+e._s(e.DatetimeUtil.formatDate(a.updated))+" ")]}},{key:"item.publishedTimestamp",fn:function fn(t){var a=t.item;return[e._v(" "+e._s(e.DatetimeUtil.formatDate(a.publishedTimestamp))+" ")]}},{key:"item.published",fn:function fn(a){var i=a.item;return[t(s["a"],{directives:[{name:"show",rawName:"v-show",value:i.published,expression:"item.published"}],attrs:{medium:"",title:"Structure Published"}},[e._v(" fab fa-product-hunt ")])]}},{key:"expanded-item",fn:function fn(a){var i=a.item;return[t("td",{key:i.id,staticClass:"pa-6",attrs:{colspan:e.headers.length}},[t("pre",[e._v(e._s(null===i||void 0===i?void 0:i.entityDefinition))])])]}},{key:"additional-actions",fn:function fn(a){var r=a.item;return[t(i["a"],{directives:[{name:"show",rawName:"v-show",value:!r.item.published,expression:"!item.item.published"}],key:"publish-"+r.item.id,staticClass:"mr-2",attrs:{small:"",icon:"",loading:r["publishing"],title:"Publish"},on:{click:function click(t){return e.publish(r)}}},[t(s["a"],{attrs:{small:""}},[e._v(" fab fa-product-hunt ")])],1),t(i["a"],{directives:[{name:"show",rawName:"v-show",value:r.item.published,expression:"item.item.published"}],staticClass:"mr-2",attrs:{small:"",icon:"",title:"Manage"},on:{click:function click(t){return e.toStructureItemsPage(r.item)}}},[t(s["a"],{attrs:{small:""}},[e._v(" "+e._s(e.icons.database)+" ")])],1),t(i["a"],{directives:[{name:"show",rawName:"v-show",value:r.item.published,expression:"item.item.published"}],key:"unpublish-"+r.item.id,staticClass:"mr-2 red--text",attrs:{small:"",icon:"",loading:r["publishing"],title:"Un-Publish"},on:{click:function click(t){return e.unPublish(r)}}},[t(s["a"],{attrs:{small:""}},[e._v(" "+e._s(e.icons.unpublish)+" ")])],1)]}}])})],1)],1)],1)},c=[],o=a("c7eb"),d=a("1da1"),h=a("d4ec"),m=a("bee2"),b=a("8f33"),p=a("262e"),f=(a("14d9"),a("99af"),a("7db0"),a("d3b7"),a("9ab4")),v=a("1b40"),k=a("3eb8"),w=a("94ed"),y=a("fb4a"),g=a("943e"),x=function(e){function StructuresList(){var e;return Object(h["a"])(this,StructuresList),e=Object(b["a"])(this,StructuresList),e.headers=[{text:"",value:"data-table-expand",sortable:!1},{text:"Id",value:"id",sortable:!0},{text:"Namespace",value:"namespace",sortable:!0},{text:"Name",value:"name",sortable:!0},{text:"Description",value:"description",sortable:!1},{text:"Created",value:"created",sortable:!0},{text:"Last Updated",value:"updated",sortable:!0},{text:"Published",value:"published",sortable:!0},{text:"Published On",value:"publishedTimestamp",sortable:!0}],e.dataSource=y["e"].getStructureService(),e.loading={},e.icons={database:w["o"],unpublish:w["B"],graph:w["t"]},e}return Object(p["a"])(StructuresList,e),Object(m["a"])(StructuresList,[{key:"onAddItem",value:function onAddItem(){this.$router.push("".concat(this.$route.path,"/add"))}},{key:"onEditItem",value:function onEditItem(e){this.$router.push("".concat(this.$route.path,"/edit/").concat(e.id))}},{key:"publish",value:function(){var e=Object(d["a"])(Object(o["a"])().mark((function _callee(e){var t,a;return Object(o["a"])().wrap((function _callee$(i){while(1)switch(i.prev=i.next){case 0:if(e["publishing"]=!0,!confirm("Are you sure you want to Publish this Structure?")){i.next=15;break}return a=null===(t=this.$refs)||void 0===t?void 0:t.crudTable,i.prev=3,i.next=6,this.dataSource.publish(e.item.id);case 6:null===a||void 0===a||a.find(),delete e["publishing"],i.next=15;break;case 10:i.prev=10,i.t0=i["catch"](3),delete e["publishing"],console.error(i.t0.stack,i.t0),null===a||void 0===a||a.displayAlert(i.t0.message);case 15:case"end":return i.stop()}}),_callee,this,[[3,10]])})));function publish(t){return e.apply(this,arguments)}return publish}()},{key:"unPublish",value:function(){var e=Object(d["a"])(Object(o["a"])().mark((function _callee2(e){var t,a;return Object(o["a"])().wrap((function _callee2$(i){while(1)switch(i.prev=i.next){case 0:if(e["publishing"]=!0,!confirm("Are you sure you want to Remove Published Status for this Structure? \nAll data saved under this Structure will be permanently deleted, proceed with caution.")){i.next=15;break}return a=null===(t=this.$refs)||void 0===t?void 0:t.crudTable,i.prev=3,i.next=6,this.dataSource.unPublish(e.item.id);case 6:null===a||void 0===a||a.find(),delete e["publishing"],i.next=15;break;case 10:i.prev=10,i.t0=i["catch"](3),delete e["publishing"],console.error(i.t0.stack,i.t0),null===a||void 0===a||a.displayAlert(i.t0.message);case 15:case"end":return i.stop()}}),_callee2,this,[[3,10]])})));function unPublish(t){return e.apply(this,arguments)}return unPublish}()},{key:"toStructureItemsPage",value:function toStructureItemsPage(e){this.$router.push({path:"/entity/".concat(e.id)})}}]),StructuresList}(v["e"]);x=Object(f["a"])([Object(v["a"])({computed:{DatetimeUtil:function DatetimeUtil(){return g["a"]}},components:{CrudTable:k["a"]}}),Object(f["b"])("design:paramtypes",[])],x);var _=x,S=_,D=a("2877"),O=Object(D["a"])(S,l,c,!1,null,"756fd942",null);t["default"]=O.exports}}]);
//# sourceMappingURL=chunk-26b91843.336f7122.js.map