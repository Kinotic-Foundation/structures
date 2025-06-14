import{v as x,B as T,P as l,a2 as b,ay as w,aA as p,z as u,a0 as L,M as P,c,o as d,a as f,F as v,s as B,l as h,S as F,q as g,O as M,Q as I,t as C}from"./CwV9-nPG.js";import{R as D}from"./DD1oJGt6.js";import{s as K}from"./BZ7rLBd_.js";import"./BaRcc8XN.js";import"./DmfvTtO6.js";var S=x`
    .p-tabmenu {
        overflow-x: auto;
    }

    .p-tabmenu-tablist {
        display: flex;
        margin: 0;
        padding: 0;
        list-style-type: none;
        background: dt('tabmenu.tablist.background');
        border-style: solid;
        border-color: dt('tabmenu.tablist.border.color');
        border-width: dt('tabmenu.tablist.border.width');
        position: relative;
    }

    .p-tabmenu-item-link {
        cursor: pointer;
        user-select: none;
        display: flex;
        align-items: center;
        text-decoration: none;
        position: relative;
        overflow: hidden;
        background: dt('tabmenu.item.background');
        border-style: solid;
        border-width: dt('tabmenu.item.border.width');
        border-color: dt('tabmenu.item.border.color');
        color: dt('tabmenu.item.color');
        padding: dt('tabmenu.item.padding');
        font-weight: dt('tabmenu.item.font.weight');
        transition:
            background dt('tabmenu.transition.duration'),
            border-color dt('tabmenu.transition.duration'),
            color dt('tabmenu.transition.duration'),
            outline-color dt('tabmenu.transition.duration'),
            box-shadow dt('tabmenu.transition.duration');
        margin: dt('tabmenu.item.margin');
        outline-color: transparent;
        gap: dt('tabmenu.item.gap');
    }

    .p-tabmenu-item-link:focus-visible {
        z-index: 1;
        box-shadow: dt('tabmenu.item.focus.ring.shadow');
        outline: dt('tabmenu.item.focus.ring.width') dt('tabmenu.item.focus.ring.style') dt('tabmenu.item.focus.ring.color');
        outline-offset: dt('tabmenu.item.focus.ring.offset');
    }

    .p-tabmenu-item-icon {
        color: dt('tabmenu.item.icon.color');
        transition:
            background dt('tabmenu.transition.duration'),
            border-color dt('tabmenu.transition.duration'),
            color dt('tabmenu.transition.duration'),
            outline-color dt('tabmenu.transition.duration'),
            box-shadow dt('tabmenu.transition.duration');
    }

    .p-tabmenu-item-label {
        line-height: 1;
    }

    .p-tabmenu-item:not(.p-tabmenu-item-active):not(.p-disabled):hover .p-tabmenu-item-link {
        background: dt('tabmenu.item.hover.background');
        border-color: dt('tabmenu.item.hover.border.color');
        color: dt('tabmenu.item.hover.color');
    }

    .p-tabmenu-item:not(.p-tabmenu-item-active):not(.p-disabled):hover .p-tabmenu-item-icon {
        color: dt('tabmenu.item.icon.hover.color');
    }

    .p-tabmenu-item-active .p-tabmenu-item-link {
        background: dt('tabmenu.item.active.background');
        border-color: dt('tabmenu.item.active.border.color');
        color: dt('tabmenu.item.active.color');
    }

    .p-tabmenu-item-active .p-tabmenu-item-icon {
        color: dt('tabmenu.item.icon.active.color');
    }

    .p-tabmenu-active-bar {
        z-index: 1;
        display: block;
        position: absolute;
        bottom: dt('tabmenu.active.bar.bottom');
        height: dt('tabmenu.active.bar.height');
        background: dt('tabmenu.active.bar.background');
        transition: 250ms cubic-bezier(0.35, 0, 0.25, 1);
    }

    .p-tabmenu::-webkit-scrollbar {
        display: none;
    }
`,N={root:"p-tabmenu p-component",tablist:"p-tabmenu-tablist",item:function(e){var t=e.instance,r=e.index,s=e.item;return["p-tabmenu-item",{"p-tabmenu-item-active":t.d_activeIndex===r,"p-disabled":t.disabled(s)}]},itemLink:"p-tabmenu-item-link",itemIcon:"p-tabmenu-item-icon",itemLabel:"p-tabmenu-item-label",activeBar:"p-tabmenu-active-bar"},O=T.extend({name:"tabmenu",style:S,classes:N}),E={name:"BaseTabMenu",extends:K,props:{model:{type:Array,default:null},activeIndex:{type:Number,default:0},ariaLabelledby:{type:String,default:null},ariaLabel:{type:String,default:null}},style:O,provide:function(){return{$pcTabMenu:this,$parentInstance:this}}},$={name:"TabMenu",extends:E,inheritAttrs:!1,emits:["update:activeIndex","tab-change"],data:function(){return{d_activeIndex:this.activeIndex}},watch:{activeIndex:{flush:"post",handler:function(e){this.d_activeIndex=e,this.updateInkBar()}}},mounted:function(){var e=this;this.$nextTick(function(){e.updateInkBar()});var t=this.findActiveItem();t&&(t.tabIndex="0")},updated:function(){this.updateInkBar()},methods:{getPTOptions:function(e,t,r){return this.ptm(e,{context:{item:t,index:r}})},onItemClick:function(e,t,r){if(this.disabled(t)){e.preventDefault();return}t.command&&t.command({originalEvent:e,item:t}),r!==this.d_activeIndex&&(this.d_activeIndex=r,this.$emit("update:activeIndex",this.d_activeIndex)),this.$emit("tab-change",{originalEvent:e,index:r})},onKeydownItem:function(e,t,r){switch(e.code){case"ArrowRight":{this.navigateToNextItem(e.target),e.preventDefault();break}case"ArrowLeft":{this.navigateToPrevItem(e.target),e.preventDefault();break}case"Home":{this.navigateToFirstItem(e.target),e.preventDefault();break}case"End":{this.navigateToLastItem(e.target),e.preventDefault();break}case"Space":case"NumpadEnter":case"Enter":{this.onItemClick(e,t,r),e.preventDefault();break}case"Tab":{this.onTabKey();break}}},navigateToNextItem:function(e){var t=this.findNextItem(e);t&&this.setFocusToMenuitem(e,t)},navigateToPrevItem:function(e){var t=this.findPrevItem(e);t&&this.setFocusToMenuitem(e,t)},navigateToFirstItem:function(e){var t=this.findFirstItem(e);t&&this.setFocusToMenuitem(e,t)},navigateToLastItem:function(e){var t=this.findLastItem(e);t&&this.setFocusToMenuitem(e,t)},findNextItem:function(e){var t=e.parentElement.nextElementSibling;return t?b(t,"data-p-disabled")===!0?this.findNextItem(t.children[0]):t.children[0]:null},findPrevItem:function(e){var t=e.parentElement.previousElementSibling;return t?b(t,"data-p-disabled")===!0?this.findPrevItem(t.children[0]):t.children[0]:null},findFirstItem:function(){var e=u(this.$refs.nav,'[data-pc-section="item"][data-p-disabled="false"]');return e?e.children[0]:null},findLastItem:function(){var e=L(this.$refs.nav,'[data-pc-section="item"][data-p-disabled="false"]');return e?e[e.length-1].children[0]:null},findActiveItem:function(){var e=u(this.$refs.nav,'[data-pc-section="item"][data-p-disabled="false"][data-p-active="true"]');return e?e.children[0]:null},setFocusToMenuitem:function(e,t){e.tabIndex="-1",t.tabIndex="0",t.focus()},onTabKey:function(){var e=u(this.$refs.nav,'[data-pc-section="item"][data-p-disabled="false"][data-p-active="true"]'),t=u(this.$refs.nav,'[data-pc-section="itemlink"][tabindex="0"]');t!==e.children[0]&&(e&&(e.children[0].tabIndex="0"),t.tabIndex="-1")},visible:function(e){return typeof e.visible=="function"?e.visible():e.visible!==!1},disabled:function(e){return typeof e.disabled=="function"?e.disabled():e.disabled===!0},label:function(e){return typeof e.label=="function"?e.label():e.label},updateInkBar:function(){for(var e=this.$refs.nav.children,t=!1,r=0;r<e.length;r++){var s=e[r];b(s,"data-p-active")&&(this.$refs.inkbar.style.width=w(s)+"px",this.$refs.inkbar.style.left=p(s).left-p(this.$refs.nav).left+"px",t=!0)}t||(this.$refs.inkbar.style.width="0px",this.$refs.inkbar.style.left="0px")},getMenuItemProps:function(e,t){var r=this;return{action:l({class:this.cx("itemLink"),tabindex:-1,onClick:function(i){return r.onItemClick(i,e,t)},onKeyDown:function(i){return r.onKeydownItem(i,e,t)}},this.getPTOptions("itemLink",e,t)),icon:l({class:[this.cx("itemIcon"),e.icon]},this.getPTOptions("itemIcon",e,t)),label:l({class:this.cx("itemLabel")},this.getPTOptions("itemLabel",e,t))}}},directives:{ripple:D}},A=["aria-labelledby","aria-label"],_=["onClick","onKeydown","data-p-active","data-p-disabled"],z=["href","target","aria-label","aria-disabled"];function R(a,e,t,r,s,i){var k=P("ripple");return d(),c("div",l({class:a.cx("root")},a.ptmi("root")),[f("ul",l({ref:"nav",class:a.cx("tablist"),role:"menubar","aria-labelledby":a.ariaLabelledby,"aria-label":a.ariaLabel},a.ptm("tablist")),[(d(!0),c(v,null,B(a.model,function(n,o){return d(),c(v,{key:i.label(n)+"_"+o.toString()},[i.visible(n)?(d(),c("li",l({key:0,ref_for:!0,ref:"tab",class:[a.cx("item",{item:n,index:o}),n.class],role:"presentation",onClick:function(m){return i.onItemClick(m,n,o)},onKeydown:function(m){return i.onKeydownItem(m,n,o)}},{ref_for:!0},i.getPTOptions("item",n,o),{"data-p-active":s.d_activeIndex===o,"data-p-disabled":i.disabled(n)}),[a.$slots.item?(d(),g(I(a.$slots.item),{key:1,item:n,index:o,active:o===s.d_activeIndex,label:i.label(n),props:i.getMenuItemProps(n,o)},null,8,["item","index","active","label","props"])):F((d(),c("a",l({key:0,ref_for:!0,ref:"tabLink",role:"menuitem",href:n.url,class:a.cx("itemLink"),target:n.target,"aria-label":i.label(n),"aria-disabled":i.disabled(n),tabindex:-1},{ref_for:!0},i.getPTOptions("itemLink",n,o)),[a.$slots.itemicon?(d(),g(I(a.$slots.itemicon),{key:0,item:n,class:M(a.cx("itemIcon"))},null,8,["item","class"])):n.icon?(d(),c("span",l({key:1,class:[a.cx("itemIcon"),n.icon]},{ref_for:!0},i.getPTOptions("itemIcon",n,o)),null,16)):h("",!0),f("span",l({class:a.cx("itemLabel")},{ref_for:!0},i.getPTOptions("itemLabel",n,o)),C(i.label(n)),17)],16,z)),[[k]])],16,_)):h("",!0)],64)}),128)),f("li",l({ref:"inkbar",role:"none",class:a.cx("activeBar")},a.ptm("activeBar")),null,16)],16,A)],16)}$.render=R;export{$ as default};
