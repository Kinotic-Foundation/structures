import{v as O,B as R,x as c,z as L,y as M,D as G,J as S,A as p,U as N,aD as F,P as l,L as K,M as q,c as d,o as u,l as h,t as A,F as v,s as w,a as P,S as U,q as y,O as V,Q as k,p as E,N as x,b as z,aa as H,bh as j}from"./CwV9-nPG.js";import{Z as C}from"./DGEOgZpe.js";import{s as Z}from"./JNyuClsA.js";import{s as D}from"./BZ7rLBd_.js";import{s as J}from"./1FoLdTXl.js";import{s as Q}from"./3DPyOw_r.js";import{R as W}from"./DD1oJGt6.js";import"./BrX9DCZU.js";import"./BaRcc8XN.js";import"./DmfvTtO6.js";var X=O`
    .p-megamenu {
        position: relative;
        display: flex;
        align-items: center;
        background: dt('megamenu.background');
        border: 1px solid dt('megamenu.border.color');
        border-radius: dt('megamenu.border.radius');
        color: dt('megamenu.color');
        gap: dt('megamenu.gap');
    }

    .p-megamenu-start,
    .p-megamenu-end {
        display: flex;
        align-items: center;
    }

    .p-megamenu-root-list {
        margin: 0;
        padding: 0;
        list-style: none;
        outline: 0 none;
        align-items: center;
        display: flex;
        flex-wrap: wrap;
        gap: dt('megamenu.gap');
    }

    .p-megamenu-root-list > .p-megamenu-item > .p-megamenu-item-content {
        border-radius: dt('megamenu.base.item.border.radius');
    }

    .p-megamenu-root-list > .p-megamenu-item > .p-megamenu-item-content > .p-megamenu-item-link {
        padding: dt('megamenu.base.item.padding');
    }

    .p-megamenu-item-content {
        transition:
            background dt('megamenu.transition.duration'),
            color dt('megamenu.transition.duration');
        border-radius: dt('megamenu.item.border.radius');
        color: dt('megamenu.item.color');
    }

    .p-megamenu-item-link {
        cursor: pointer;
        display: flex;
        align-items: center;
        text-decoration: none;
        overflow: hidden;
        position: relative;
        color: inherit;
        padding: dt('megamenu.item.padding');
        gap: dt('megamenu.item.gap');
        user-select: none;
        outline: 0 none;
    }

    .p-megamenu-item-label {
        line-height: 1;
    }

    .p-megamenu-item-icon {
        color: dt('megamenu.item.icon.color');
    }

    .p-megamenu-submenu-icon {
        color: dt('megamenu.submenu.icon.color');
        font-size: dt('megamenu.submenu.icon.size');
        width: dt('megamenu.submenu.icon.size');
        height: dt('megamenu.submenu.icon.size');
    }

    .p-megamenu-item.p-focus > .p-megamenu-item-content {
        color: dt('megamenu.item.focus.color');
        background: dt('megamenu.item.focus.background');
    }

    .p-megamenu-item.p-focus > .p-megamenu-item-content .p-megamenu-item-icon {
        color: dt('megamenu.item.icon.focus.color');
    }

    .p-megamenu-item.p-focus > .p-megamenu-item-content .p-megamenu-submenu-icon {
        color: dt('megamenu.submenu.icon.focus.color');
    }

    .p-megamenu-item:not(.p-disabled) > .p-megamenu-item-content:hover {
        color: dt('megamenu.item.focus.color');
        background: dt('megamenu.item.focus.background');
    }

    .p-megamenu-item:not(.p-disabled) > .p-megamenu-item-content:hover .p-megamenu-item-icon {
        color: dt('megamenu.item.icon.focus.color');
    }

    .p-megamenu-item:not(.p-disabled) > .p-megamenu-item-content:hover .p-megamenu-submenu-icon {
        color: dt('megamenu.submenu.icon.focus.color');
    }

    .p-megamenu-item-active > .p-megamenu-item-content {
        color: dt('megamenu.item.active.color');
        background: dt('megamenu.item.active.background');
    }

    .p-megamenu-item-active > .p-megamenu-item-content .p-megamenu-item-icon {
        color: dt('megamenu.item.icon.active.color');
    }

    .p-megamenu-item-active > .p-megamenu-item-content .p-megamenu-submenu-icon {
        color: dt('megamenu.submenu.icon.active.color');
    }

    .p-megamenu-overlay {
        display: none;
        position: absolute;
        width: auto;
        z-index: 1;
        left: 0;
        min-width: 100%;
        padding: dt('megamenu.overlay.padding');
        background: dt('megamenu.overlay.background');
        color: dt('megamenu.overlay.color');
        border: 1px solid dt('megamenu.overlay.border.color');
        border-radius: dt('megamenu.overlay.border.radius');
        box-shadow: dt('megamenu.overlay.shadow');
    }

    .p-megamenu-overlay:dir(rtl) {
        left: auto;
        right: 0;
    }

    .p-megamenu-root-list > .p-megamenu-item-active > .p-megamenu-overlay {
        display: block;
    }

    .p-megamenu-submenu {
        margin: 0;
        list-style: none;
        padding: dt('megamenu.submenu.padding');
        min-width: 12.5rem;
        display: flex;
        flex-direction: column;
        gap: dt('megamenu.submenu.gap');
    }

    .p-megamenu-submenu-label {
        padding: dt('megamenu.submenu.label.padding');
        color: dt('megamenu.submenu.label.color');
        font-weight: dt('megamenu.submenu.label.font.weight');
        background: dt('megamenu.submenu.label.background');
    }

    .p-megamenu-separator {
        border-block-start: 1px solid dt('megamenu.separator.border.color');
    }

    .p-megamenu-horizontal {
        align-items: center;
        padding: dt('megamenu.horizontal.orientation.padding');
    }

    .p-megamenu-horizontal .p-megamenu-root-list {
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        gap: dt('megamenu.horizontal.orientation.gap');
    }

    .p-megamenu-horizontal .p-megamenu-end {
        margin-left: auto;
        align-self: center;
    }

    .p-megamenu-horizontal .p-megamenu-end:dir(rtl) {
        margin-left: 0;
        margin-right: auto;
    }

    .p-megamenu-vertical {
        display: inline-flex;
        min-width: 12.5rem;
        flex-direction: column;
        align-items: stretch;
        padding: dt('megamenu.vertical.orientation.padding');
    }

    .p-megamenu-vertical .p-megamenu-root-list {
        align-items: stretch;
        flex-direction: column;
        gap: dt('megamenu.vertical.orientation.gap');
    }

    .p-megamenu-vertical .p-megamenu-root-list > .p-megamenu-item-active > .p-megamenu-overlay {
        left: 100%;
        top: 0;
    }

    .p-megamenu-vertical .p-megamenu-root-list > .p-megamenu-item-active > .p-megamenu-overlay:dir(rtl) {
        left: auto;
        right: 100%;
    }

    .p-megamenu-vertical .p-megamenu-root-list > .p-megamenu-item > .p-megamenu-item-content .p-megamenu-submenu-icon {
        margin-left: auto;
    }

    .p-megamenu-vertical .p-megamenu-root-list > .p-megamenu-item > .p-megamenu-item-content .p-megamenu-submenu-icon:dir(rtl) {
        margin-left: 0;
        margin-right: auto;
        transform: rotate(180deg);
    }

    .p-megamenu-grid {
        display: flex;
    }

    .p-megamenu-col-2,
    .p-megamenu-col-3,
    .p-megamenu-col-4,
    .p-megamenu-col-6,
    .p-megamenu-col-12 {
        flex: 0 0 auto;
        padding: dt('megamenu.overlay.gap');
    }

    .p-megamenu-col-2 {
        width: 16.6667%;
    }

    .p-megamenu-col-3 {
        width: 25%;
    }

    .p-megamenu-col-4 {
        width: 33.3333%;
    }

    .p-megamenu-col-6 {
        width: 50%;
    }

    .p-megamenu-col-12 {
        width: 100%;
    }

    .p-megamenu-button {
        display: none;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        width: dt('megamenu.mobile.button.size');
        height: dt('megamenu.mobile.button.size');
        position: relative;
        color: dt('megamenu.mobile.button.color');
        border: 0 none;
        background: transparent;
        border-radius: dt('megamenu.mobile.button.border.radius');
        transition:
            background dt('megamenu.transition.duration'),
            color dt('megamenu.transition.duration'),
            outline-color dt('megamenu.transition.duration'),
            box-shadow dt('megamenu.transition.duration');
        outline-color: transparent;
    }

    .p-megamenu-button:hover {
        color: dt('megamenu.mobile.button.hover.color');
        background: dt('megamenu.mobile.button.hover.background');
    }

    .p-megamenu-button:focus-visible {
        box-shadow: dt('megamenu.mobile.button.focus.ring.shadow');
        outline: dt('megamenu.mobile.button.focus.ring.width') dt('megamenu.mobile.button.focus.ring.style') dt('megamenu.mobile.button.focus.ring.color');
        outline-offset: dt('megamenu.mobile.button.focus.ring.offset');
    }

    .p-megamenu-mobile {
        display: flex;
    }

    .p-megamenu-mobile .p-megamenu-button {
        display: flex;
    }

    .p-megamenu-mobile .p-megamenu-root-list {
        position: absolute;
        display: none;
        flex-direction: column;
        top: 100%;
        left: 0;
        z-index: 1;
        width: 100%;
        padding: dt('megamenu.submenu.padding');
        gap: dt('megamenu.submenu.gap');
        background: dt('megamenu.overlay.background');
        border: 1px solid dt('megamenu.overlay.border.color');
        box-shadow: dt('megamenu.overlay.shadow');
    }

    .p-megamenu-mobile .p-megamenu-root-list:dir(rtl) {
        left: auto;
        right: 0;
    }

    .p-megamenu-mobile-active .p-megamenu-root-list {
        display: block;
    }

    .p-megamenu-mobile .p-megamenu-root-list .p-megamenu-item {
        width: 100%;
        position: static;
    }

    .p-megamenu-mobile .p-megamenu-overlay {
        position: static;
        border: 0 none;
        border-radius: 0;
        box-shadow: none;
    }

    .p-megamenu-mobile .p-megamenu-grid {
        flex-wrap: wrap;
        overflow: auto;
        max-height: 90%;
    }

    .p-megamenu-mobile .p-megamenu-root-list > .p-megamenu-item > .p-megamenu-item-content .p-megamenu-submenu-icon {
        margin-left: auto;
        transition: transform 0.2s;
    }

    .p-megamenu-mobile .p-megamenu-root-list > .p-megamenu-item > .p-megamenu-item-content .p-megamenu-submenu-icon:dir(rtl) {
        margin-left: 0;
        margin-right: auto;
    }

    .p-megamenu-mobile .p-megamenu-root-list > .p-megamenu-item-active > .p-megamenu-item-content .p-megamenu-submenu-icon {
        transform: rotate(-180deg);
    }
`,Y={rootList:function(e){var i=e.props;return{"max-height":i.scrollHeight,overflow:"auto"}}},_={root:function(e){var i=e.instance;return["p-megamenu p-component",{"p-megamenu-mobile":i.queryMatches,"p-megamenu-mobile-active":i.mobileActive,"p-megamenu-horizontal":i.horizontal,"p-megamenu-vertical":i.vertical}]},start:"p-megamenu-start",button:"p-megamenu-button",rootList:"p-megamenu-root-list",submenuLabel:function(e){var i=e.instance,a=e.processedItem;return["p-megamenu-submenu-label",{"p-disabled":i.isItemDisabled(a)}]},item:function(e){var i=e.instance,a=e.processedItem;return["p-megamenu-item",{"p-megamenu-item-active":i.isItemActive(a),"p-focus":i.isItemFocused(a),"p-disabled":i.isItemDisabled(a)}]},itemContent:"p-megamenu-item-content",itemLink:"p-megamenu-item-link",itemIcon:"p-megamenu-item-icon",itemLabel:"p-megamenu-item-label",submenuIcon:"p-megamenu-submenu-icon",overlay:"p-megamenu-overlay",grid:"p-megamenu-grid",column:function(e){var i=e.instance,a=e.processedItem,m=i.isItemGroup(a)?a.items.length:0,n;if(i.$parentInstance.queryMatches)n="p-megamenu-col-12";else switch(m){case 2:n="p-megamenu-col-6";break;case 3:n="p-megamenu-col-4";break;case 4:n="p-megamenu-col-3";break;case 6:n="p-megamenu-col-2";break;default:n="p-megamenu-col-12";break}return n},submenu:"p-megamenu-submenu",separator:"p-megamenu-separator",end:"p-megamenu-end"},$=R.extend({name:"megamenu",style:X,classes:_,inlineStyles:Y}),ee={name:"BaseMegaMenu",extends:D,props:{model:{type:Array,default:null},orientation:{type:String,default:"horizontal"},breakpoint:{type:String,default:"960px"},disabled:{type:Boolean,default:!1},tabindex:{type:Number,default:0},scrollHeight:{type:String,default:"20rem"},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:$,provide:function(){return{$pcMegaMenu:this,$parentInstance:this}}},T={name:"MegaMenuSub",hostName:"MegaMenu",extends:D,emits:["item-click","item-mouseenter"],props:{menuId:{type:String,default:null},focusedItemId:{type:String,default:null},horizontal:{type:Boolean,default:!1},submenu:{type:Object,default:null},mobileActive:{type:Boolean,default:!1},items:{type:Array,default:null},level:{type:Number,default:0},templates:{type:Object,default:null},activeItem:{type:Object,default:null},tabindex:{type:Number,default:0}},methods:{getSubListId:function(e){return"".concat(this.getItemId(e),"_list")},getSubListKey:function(e){return this.getSubListId(e)},getItemId:function(e){return"".concat(this.menuId,"_").concat(e.key)},getItemKey:function(e){return this.getItemId(e)},getItemProp:function(e,i,a){return e&&e.item?F(e.item[i],a):void 0},getItemLabel:function(e){return this.getItemProp(e,"label")},getPTOptions:function(e,i,a){return this.ptm(a,{context:{item:e.item,index:i,active:this.isItemActive(e),focused:this.isItemFocused(e),disabled:this.isItemDisabled(e)}})},isItemActive:function(e){return c(this.activeItem)?this.activeItem.key===e.key:!1},isItemVisible:function(e){return this.getItemProp(e,"visible")!==!1},isItemDisabled:function(e){return this.getItemProp(e,"disabled")},isItemFocused:function(e){return this.focusedItemId===this.getItemId(e)},isItemGroup:function(e){return c(e.items)},onItemClick:function(e,i){this.getItemProp(i,"command",{originalEvent:e,item:i.item}),this.$emit("item-click",{originalEvent:e,processedItem:i,isFocus:!0})},onItemMouseEnter:function(e,i){this.$emit("item-mouseenter",{originalEvent:e,processedItem:i})},getAriaSetSize:function(){var e=this;return this.items.filter(function(i){return e.isItemVisible(i)&&!e.getItemProp(i,"separator")}).length},getAriaPosInset:function(e){var i=this;return e-this.items.slice(0,e).filter(function(a){return i.isItemVisible(a)&&i.getItemProp(a,"separator")}).length+1},getMenuItemProps:function(e,i){return{action:l({class:this.cx("itemLink"),tabindex:-1},this.getPTOptions(e,i,"itemLink")),icon:l({class:[this.cx("itemIcon"),this.getItemProp(e,"icon")]},this.getPTOptions(e,i,"itemIcon")),label:l({class:this.cx("label")},this.getPTOptions(e,i,"label")),submenuicon:l({class:this.cx("submenuIcon")},this.getPTOptions(e,i,"submenuIcon"))}}},components:{AngleRightIcon:Q,AngleDownIcon:J},directives:{ripple:W}},te=["tabindex"],ie=["id","aria-label","aria-disabled","aria-expanded","aria-haspopup","aria-level","aria-setsize","aria-posinset","data-p-active","data-p-focused","data-p-disabled"],ne=["onClick","onMouseenter"],ae=["href","target"],se=["id"];function me(t,e,i,a,m,n){var o=K("MegaMenuSub",!0),f=q("ripple");return u(),d("ul",l({class:i.level===0?t.cx("rootList"):t.cx("submenu"),tabindex:i.tabindex},i.level===0?t.ptm("rootList"):t.ptm("submenu")),[i.submenu?(u(),d("li",l({key:0,class:[t.cx("submenuLabel",{submenu:i.submenu}),n.getItemProp(i.submenu,"class")],style:n.getItemProp(i.submenu,"style"),role:"presentation"},t.ptm("submenuLabel")),A(n.getItemLabel(i.submenu)),17)):h("",!0),(u(!0),d(v,null,w(i.items,function(s,r){return u(),d(v,{key:n.getItemKey(s)},[n.isItemVisible(s)&&!n.getItemProp(s,"separator")?(u(),d("li",l({key:0,id:n.getItemId(s),style:n.getItemProp(s,"style"),class:[t.cx("item",{processedItem:s}),n.getItemProp(s,"class")],role:"menuitem","aria-label":n.getItemLabel(s),"aria-disabled":n.isItemDisabled(s)||void 0,"aria-expanded":n.isItemGroup(s)?n.isItemActive(s):void 0,"aria-haspopup":n.isItemGroup(s)&&!n.getItemProp(s,"to")?"menu":void 0,"aria-level":i.level+1,"aria-setsize":n.getAriaSetSize(),"aria-posinset":n.getAriaPosInset(r)},{ref_for:!0},n.getPTOptions(s,r,"item"),{"data-p-active":n.isItemActive(s),"data-p-focused":n.isItemFocused(s),"data-p-disabled":n.isItemDisabled(s)}),[P("div",l({class:t.cx("itemContent"),onClick:function(g){return n.onItemClick(g,s)},onMouseenter:function(g){return n.onItemMouseEnter(g,s)}},{ref_for:!0},n.getPTOptions(s,r,"itemContent")),[i.templates.item?(u(),y(k(i.templates.item),{key:1,item:s.item,hasSubmenu:n.isItemGroup(s),label:n.getItemLabel(s),props:n.getMenuItemProps(s,r)},null,8,["item","hasSubmenu","label","props"])):U((u(),d("a",l({key:0,href:n.getItemProp(s,"url"),class:t.cx("itemLink"),target:n.getItemProp(s,"target"),tabindex:"-1"},{ref_for:!0},n.getPTOptions(s,r,"itemLink")),[i.templates.itemicon?(u(),y(k(i.templates.itemicon),{key:0,item:s.item,class:V(t.cx("itemIcon"))},null,8,["item","class"])):n.getItemProp(s,"icon")?(u(),d("span",l({key:1,class:[t.cx("itemIcon"),n.getItemProp(s,"icon")]},{ref_for:!0},n.getPTOptions(s,r,"itemIcon")),null,16)):h("",!0),P("span",l({class:t.cx("itemLabel")},{ref_for:!0},n.getPTOptions(s,r,"itemLabel")),A(n.getItemLabel(s)),17),n.isItemGroup(s)?(u(),d(v,{key:2},[i.templates.submenuicon?(u(),y(k(i.templates.submenuicon),l({key:0,active:n.isItemActive(s),class:t.cx("submenuIcon")},{ref_for:!0},n.getPTOptions(s,r,"submenuIcon")),null,16,["active","class"])):(u(),y(k(i.horizontal||i.mobileActive?"AngleDownIcon":"AngleRightIcon"),l({key:1,class:t.cx("submenuIcon")},{ref_for:!0},n.getPTOptions(s,r,"submenuIcon")),null,16,["class"]))],64)):h("",!0)],16,ae)),[[f]])],16,ne),n.isItemVisible(s)&&n.isItemGroup(s)?(u(),d("div",l({key:0,class:t.cx("overlay")},{ref_for:!0},t.ptm("overlay")),[P("div",l({class:t.cx("grid")},{ref_for:!0},t.ptm("grid")),[(u(!0),d(v,null,w(s.items,function(I){return u(),d("div",l({key:n.getItemKey(I),class:t.cx("column",{processedItem:s})},{ref_for:!0},t.ptm("column")),[(u(!0),d(v,null,w(I,function(g){return u(),y(o,{key:n.getSubListKey(g),id:n.getSubListId(g),style:E(t.sx("submenu",!0,{processedItem:s})),role:"menu",menuId:i.menuId,focusedItemId:i.focusedItemId,submenu:g,items:g.items,templates:i.templates,level:i.level+1,mobileActive:i.mobileActive,pt:t.pt,unstyled:t.unstyled,onItemClick:e[0]||(e[0]=function(b){return t.$emit("item-click",b)}),onItemMouseenter:e[1]||(e[1]=function(b){return t.$emit("item-mouseenter",b)})},null,8,["id","style","menuId","focusedItemId","submenu","items","templates","level","mobileActive","pt","unstyled"])}),128))],16)}),128))],16)],16)):h("",!0)],16,ie)):h("",!0),n.isItemVisible(s)&&n.getItemProp(s,"separator")?(u(),d("li",l({key:1,id:n.getItemId(s),class:[t.cx("separator"),n.getItemProp(s,"class")],style:n.getItemProp(s,"style"),role:"separator"},{ref_for:!0},t.ptm("separator")),null,16,se)):h("",!0)],64)}),128))],16,te)}T.render=me;var oe={name:"MegaMenu",extends:ee,inheritAttrs:!1,emits:["focus","blur"],outsideClickListener:null,resizeListener:null,matchMediaListener:null,container:null,menubar:null,searchTimeout:null,searchValue:null,data:function(){return{mobileActive:!1,focused:!1,focusedItemInfo:{index:-1,key:"",parentKey:""},activeItem:null,dirty:!1,query:null,queryMatches:!1}},watch:{activeItem:function(e){c(e)?(this.bindOutsideClickListener(),this.bindResizeListener()):(this.unbindOutsideClickListener(),this.unbindResizeListener())}},mounted:function(){this.bindMatchMediaListener()},beforeUnmount:function(){this.mobileActive=!1,this.unbindOutsideClickListener(),this.unbindResizeListener(),this.unbindMatchMediaListener()},methods:{getItemProp:function(e,i){return e?F(e[i]):void 0},getItemLabel:function(e){return this.getItemProp(e,"label")},isItemDisabled:function(e){return this.getItemProp(e,"disabled")},isItemVisible:function(e){return this.getItemProp(e,"visible")!==!1},isItemGroup:function(e){return c(this.getItemProp(e,"items"))},isItemSeparator:function(e){return this.getItemProp(e,"separator")},getProccessedItemLabel:function(e){return e?this.getItemLabel(e.item):void 0},isProccessedItemGroup:function(e){return e&&c(e.items)},toggle:function(e){var i=this;this.mobileActive?(this.mobileActive=!1,C.clear(this.menubar),this.hide()):(this.mobileActive=!0,C.set("menu",this.menubar,this.$primevue.config.zIndex.menu),setTimeout(function(){i.show()},1)),this.bindOutsideClickListener(),e.preventDefault()},show:function(){this.focusedItemInfo={index:this.findFirstFocusedItemIndex(),level:0,parentKey:""},p(this.menubar)},hide:function(e,i){var a=this;this.mobileActive&&(this.mobileActive=!1,setTimeout(function(){p(a.$refs.menubutton,{preventScroll:!0})},1)),this.activeItem=null,this.focusedItemInfo={index:-1,key:"",parentKey:""},i&&p(this.menubar),this.dirty=!1},onFocus:function(e){if(this.focused=!0,this.focusedItemInfo.index===-1){var i=this.findFirstFocusedItemIndex(),a=this.findVisibleItem(i);this.focusedItemInfo={index:i,key:a.key,parentKey:a.parentKey}}this.$emit("focus",e)},onBlur:function(e){this.focused=!1,this.focusedItemInfo={index:-1,key:"",parentKey:""},this.searchValue="",this.dirty=!1,this.$emit("blur",e)},onKeyDown:function(e){if(this.disabled){e.preventDefault();return}var i=e.metaKey||e.ctrlKey;switch(e.code){case"ArrowDown":this.onArrowDownKey(e);break;case"ArrowUp":this.onArrowUpKey(e);break;case"ArrowLeft":this.onArrowLeftKey(e);break;case"ArrowRight":this.onArrowRightKey(e);break;case"Home":this.onHomeKey(e);break;case"End":this.onEndKey(e);break;case"Space":this.onSpaceKey(e);break;case"Enter":case"NumpadEnter":this.onEnterKey(e);break;case"Escape":this.onEscapeKey(e);break;case"Tab":this.onTabKey(e);break;case"PageDown":case"PageUp":case"Backspace":case"ShiftLeft":case"ShiftRight":break;default:!i&&N(e.key)&&this.searchItems(e,e.key);break}},onItemChange:function(e){var i=e.processedItem,a=e.isFocus;if(!S(i)){var m=i.index,n=i.key,o=i.parentKey,f=i.items,s=c(f);s&&(this.activeItem=i),this.focusedItemInfo={index:m,key:n,parentKey:o},s&&(this.dirty=!0),a&&p(this.menubar)}},onItemClick:function(e){var i=e.originalEvent,a=e.processedItem,m=this.isProccessedItemGroup(a),n=S(a.parent),o=this.isSelected(a);if(o){var f=a.index,s=a.key,r=a.parentKey;this.activeItem=null,this.focusedItemInfo={index:f,key:s,parentKey:r},this.dirty=!n,this.mobileActive||p(this.menubar,{preventScroll:!0})}else m?this.onItemChange(e):this.hide(i)},onItemMouseEnter:function(e){!this.mobileActive&&this.dirty&&this.onItemChange(e)},menuButtonClick:function(e){this.toggle(e)},menuButtonKeydown:function(e){(e.code==="Enter"||e.code==="NumpadEnter"||e.code==="Space")&&this.menuButtonClick(e)},onArrowDownKey:function(e){if(this.horizontal)if(c(this.activeItem)&&this.activeItem.key===this.focusedItemInfo.key)this.focusedItemInfo={index:-1,key:"",parentKey:this.activeItem.key};else{var i=this.findVisibleItem(this.focusedItemInfo.index),a=this.isProccessedItemGroup(i);a&&(this.onItemChange({originalEvent:e,processedItem:i}),this.focusedItemInfo={index:-1,key:i.key,parentKey:i.parentKey},this.searchValue="")}var m=this.focusedItemInfo.index!==-1?this.findNextItemIndex(this.focusedItemInfo.index):this.findFirstFocusedItemIndex();this.changeFocusedItemInfo(e,m),e.preventDefault()},onArrowUpKey:function(e){if(e.altKey&&this.horizontal){if(this.focusedItemInfo.index!==-1){var i=this.findVisibleItem(this.focusedItemInfo.index),a=this.isProccessedItemGroup(i);!a&&c(this.activeItem)&&(this.focusedItemInfo.index===0?(this.focusedItemInfo={index:this.activeItem.index,key:this.activeItem.key,parentKey:this.activeItem.parentKey},this.activeItem=null):this.changeFocusedItemInfo(e,this.findFirstItemIndex()))}e.preventDefault()}else{var m=this.focusedItemInfo.index!==-1?this.findPrevItemIndex(this.focusedItemInfo.index):this.findLastFocusedItemIndex();this.changeFocusedItemInfo(e,m),e.preventDefault()}},onArrowLeftKey:function(e){var i=this.findVisibleItem(this.focusedItemInfo.index),a=this.isProccessedItemGroup(i);if(a){if(this.horizontal){var m=this.focusedItemInfo.index!==-1?this.findPrevItemIndex(this.focusedItemInfo.index):this.findLastFocusedItemIndex();this.changeFocusedItemInfo(e,m)}}else{this.vertical&&c(this.activeItem)&&i.columnIndex===0&&(this.focusedItemInfo={index:this.activeItem.index,key:this.activeItem.key,parentKey:this.activeItem.parentKey},this.activeItem=null);var n=i.columnIndex-1,o=this.visibleItems.findIndex(function(f){return f.columnIndex===n});o!==-1&&this.changeFocusedItemInfo(e,o)}e.preventDefault()},onArrowRightKey:function(e){var i=this.findVisibleItem(this.focusedItemInfo.index),a=this.isProccessedItemGroup(i);if(a){if(this.vertical)if(c(this.activeItem)&&this.activeItem.key===i.key)this.focusedItemInfo={index:-1,key:"",parentKey:this.activeItem.key};else{var m=this.findVisibleItem(this.focusedItemInfo.index),n=this.isProccessedItemGroup(m);n&&(this.onItemChange({originalEvent:e,processedItem:m}),this.focusedItemInfo={index:-1,key:m.key,parentKey:m.parentKey},this.searchValue="")}var o=this.focusedItemInfo.index!==-1?this.findNextItemIndex(this.focusedItemInfo.index):this.findFirstFocusedItemIndex();this.changeFocusedItemInfo(e,o)}else{var f=i.columnIndex+1,s=this.visibleItems.findIndex(function(r){return r.columnIndex===f});s!==-1&&this.changeFocusedItemInfo(e,s)}e.preventDefault()},onHomeKey:function(e){this.changeFocusedItemInfo(e,this.findFirstItemIndex()),e.preventDefault()},onEndKey:function(e){this.changeFocusedItemInfo(e,this.findLastItemIndex()),e.preventDefault()},onEnterKey:function(e){if(this.focusedItemInfo.index!==-1){var i=L(this.menubar,'li[id="'.concat("".concat(this.focusedItemId),'"]')),a=i&&L(i,'a[data-pc-section="itemlink"]');a?a.click():i&&i.click();var m=this.visibleItems[this.focusedItemInfo.index],n=this.isProccessedItemGroup(m);!n&&this.changeFocusedItemInfo(e,this.findFirstFocusedItemIndex())}e.preventDefault()},onSpaceKey:function(e){this.onEnterKey(e)},onEscapeKey:function(e){c(this.activeItem)&&(this.focusedItemInfo={index:this.activeItem.index,key:this.activeItem.key},this.activeItem=null),e.preventDefault()},onTabKey:function(e){if(this.focusedItemInfo.index!==-1){var i=this.findVisibleItem(this.focusedItemInfo.index),a=this.isProccessedItemGroup(i);!a&&this.onItemChange({originalEvent:e,processedItem:i})}this.hide()},bindOutsideClickListener:function(){var e=this;this.outsideClickListener||(this.outsideClickListener=function(i){var a=e.container&&!e.container.contains(i.target),m=!(e.target&&(e.target===i.target||e.target.contains(i.target)));a&&m&&e.hide()},document.addEventListener("click",this.outsideClickListener,!0))},unbindOutsideClickListener:function(){this.outsideClickListener&&(document.removeEventListener("click",this.outsideClickListener,!0),this.outsideClickListener=null)},bindResizeListener:function(){var e=this;this.resizeListener||(this.resizeListener=function(i){G()||e.hide(i,!0),e.mobileActive=!1},window.addEventListener("resize",this.resizeListener))},unbindResizeListener:function(){this.resizeListener&&(window.removeEventListener("resize",this.resizeListener),this.resizeListener=null)},bindMatchMediaListener:function(){var e=this;if(!this.matchMediaListener){var i=matchMedia("(max-width: ".concat(this.breakpoint,")"));this.query=i,this.queryMatches=i.matches,this.matchMediaListener=function(){e.queryMatches=i.matches,e.mobileActive=!1},this.query.addEventListener("change",this.matchMediaListener)}},unbindMatchMediaListener:function(){this.matchMediaListener&&(this.query.removeEventListener("change",this.matchMediaListener),this.matchMediaListener=null)},isItemMatched:function(e){var i;return this.isValidItem(e)&&((i=this.getProccessedItemLabel(e))===null||i===void 0?void 0:i.toLocaleLowerCase().startsWith(this.searchValue.toLocaleLowerCase()))},isValidItem:function(e){return!!e&&!this.isItemDisabled(e.item)&&!this.isItemSeparator(e.item)&&this.isItemVisible(e.item)},isValidSelectedItem:function(e){return this.isValidItem(e)&&this.isSelected(e)},isSelected:function(e){return c(this.activeItem)?this.activeItem.key===e.key:!1},findFirstItemIndex:function(){var e=this;return this.visibleItems.findIndex(function(i){return e.isValidItem(i)})},findLastItemIndex:function(){var e=this;return M(this.visibleItems,function(i){return e.isValidItem(i)})},findNextItemIndex:function(e){var i=this,a=e<this.visibleItems.length-1?this.visibleItems.slice(e+1).findIndex(function(m){return i.isValidItem(m)}):-1;return a>-1?a+e+1:e},findPrevItemIndex:function(e){var i=this,a=e>0?M(this.visibleItems.slice(0,e),function(m){return i.isValidItem(m)}):-1;return a>-1?a:e},findSelectedItemIndex:function(){var e=this;return this.visibleItems.findIndex(function(i){return e.isValidSelectedItem(i)})},findFirstFocusedItemIndex:function(){var e=this.findSelectedItemIndex();return e<0?this.findFirstItemIndex():e},findLastFocusedItemIndex:function(){var e=this.findSelectedItemIndex();return e<0?this.findLastItemIndex():e},findVisibleItem:function(e){return c(this.visibleItems)?this.visibleItems[e]:null},searchItems:function(e,i){var a=this;this.searchValue=(this.searchValue||"")+i;var m=-1,n=!1;return this.focusedItemInfo.index!==-1?(m=this.visibleItems.slice(this.focusedItemInfo.index).findIndex(function(o){return a.isItemMatched(o)}),m=m===-1?this.visibleItems.slice(0,this.focusedItemInfo.index).findIndex(function(o){return a.isItemMatched(o)}):m+this.focusedItemInfo.index):m=this.visibleItems.findIndex(function(o){return a.isItemMatched(o)}),m!==-1&&(n=!0),m===-1&&this.focusedItemInfo.index===-1&&(m=this.findFirstFocusedItemIndex()),m!==-1&&this.changeFocusedItemInfo(e,m),this.searchTimeout&&clearTimeout(this.searchTimeout),this.searchTimeout=setTimeout(function(){a.searchValue="",a.searchTimeout=null},500),n},changeFocusedItemInfo:function(e,i){var a=this.findVisibleItem(i);this.focusedItemInfo.index=i,this.focusedItemInfo.key=c(a)?a.key:"",this.scrollInView()},scrollInView:function(){var e=arguments.length>0&&arguments[0]!==void 0?arguments[0]:-1,i=e!==-1?"".concat(this.$id,"_").concat(e):this.focusedItemId,a;i===null&&this.queryMatches?a=this.$refs.menubutton:a=L(this.menubar,'li[id="'.concat(i,'"]')),a&&a.scrollIntoView&&a.scrollIntoView({block:"nearest",inline:"nearest",behavior:"smooth"})},createProcessedItems:function(e){var i=this,a=arguments.length>1&&arguments[1]!==void 0?arguments[1]:0,m=arguments.length>2&&arguments[2]!==void 0?arguments[2]:{},n=arguments.length>3&&arguments[3]!==void 0?arguments[3]:"",o=arguments.length>4?arguments[4]:void 0,f=[];return e&&e.forEach(function(s,r){var I=(n!==""?n+"_":"")+(o!==void 0?o+"_":"")+r,g={item:s,index:r,level:a,key:I,parent:m,parentKey:n,columnIndex:o!==void 0?o:m.columnIndex!==void 0?m.columnIndex:r};g.items=a===0&&s.items&&s.items.length>0?s.items.map(function(b,B){return i.createProcessedItems(b,a+1,g,I,B)}):i.createProcessedItems(s.items,a+1,g,I),f.push(g)}),f},containerRef:function(e){this.container=e},menubarRef:function(e){this.menubar=e?e.$el:void 0}},computed:{processedItems:function(){return this.createProcessedItems(this.model||[])},visibleItems:function(){var e=c(this.activeItem)?this.activeItem:null;return e&&e.key===this.focusedItemInfo.parentKey?e.items.reduce(function(i,a){return a.forEach(function(m){m.items.forEach(function(n){i.push(n)})}),i},[]):this.processedItems},horizontal:function(){return this.orientation==="horizontal"},vertical:function(){return this.orientation==="vertical"},focusedItemId:function(){return c(this.focusedItemInfo.key)?"".concat(this.$id,"_").concat(this.focusedItemInfo.key):null}},components:{MegaMenuSub:T,BarsIcon:Z}},re=["id"],ue=["aria-haspopup","aria-expanded","aria-controls","aria-label"];function le(t,e,i,a,m,n){var o=K("BarsIcon"),f=K("MegaMenuSub");return u(),d("div",l({ref:n.containerRef,id:t.$id,class:t.cx("root")},t.ptmi("root")),[t.$slots.start?(u(),d("div",l({key:0,class:t.cx("start")},t.ptm("start")),[x(t.$slots,"start")],16)):h("",!0),x(t.$slots,t.$slots.button?"button":"menubutton",{id:t.$id,class:V(t.cx("button")),toggleCallback:function(r){return n.menuButtonClick(r)}},function(){var s;return[t.model&&t.model.length>0?(u(),d("a",l({key:0,ref:"menubutton",role:"button",tabindex:"0",class:t.cx("button"),"aria-haspopup":!!(t.model.length&&t.model.length>0),"aria-expanded":m.mobileActive,"aria-controls":t.$id,"aria-label":(s=t.$primevue.config.locale.aria)===null||s===void 0?void 0:s.navigation,onClick:e[0]||(e[0]=function(r){return n.menuButtonClick(r)}),onKeydown:e[1]||(e[1]=function(r){return n.menuButtonKeydown(r)})},t.ptm("button")),[x(t.$slots,t.$slots.buttonicon?"buttonicon":"menubuttonicon",{},function(){return[z(o,H(j(t.ptm("buttonIcon"))),null,16)]})],16,ue)):h("",!0)]}),z(f,{ref:n.menubarRef,id:t.$id+"_list",tabindex:t.disabled?-1:t.tabindex,role:"menubar","aria-label":t.ariaLabel,"aria-labelledby":t.ariaLabelledby,"aria-disabled":t.disabled||void 0,"aria-orientation":t.orientation,"aria-activedescendant":m.focused?n.focusedItemId:void 0,menuId:t.$id,focusedItemId:m.focused?n.focusedItemId:void 0,items:n.processedItems,horizontal:n.horizontal,templates:t.$slots,activeItem:m.activeItem,mobileActive:m.mobileActive,level:0,style:E(t.sx("rootList")),pt:t.pt,unstyled:t.unstyled,onFocus:n.onFocus,onBlur:n.onBlur,onKeydown:n.onKeyDown,onItemClick:n.onItemClick,onItemMouseenter:n.onItemMouseEnter},null,8,["id","tabindex","aria-label","aria-labelledby","aria-disabled","aria-orientation","aria-activedescendant","menuId","focusedItemId","items","horizontal","templates","activeItem","mobileActive","style","pt","unstyled","onFocus","onBlur","onKeydown","onItemClick","onItemMouseenter"]),t.$slots.end?(u(),d("div",l({key:1,class:t.cx("end")},t.ptm("end")),[x(t.$slots,"end")],16)):h("",!0)],16,re)}oe.render=le;export{oe as default};
