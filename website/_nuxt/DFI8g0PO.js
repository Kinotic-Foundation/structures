import{s as A}from"./QlyqZk-1.js";import{s as P}from"./CEUF_C_A.js";import k from"./CM9zz7Wl.js";import _ from"./DeuMGsKJ.js";import I from"./WKOz3dOG.js";import{s as C}from"./B-kZlFfU.js";import{v as $,B as x,P as s,L as v,c as f,o as i,N as w,F as B,s as N,q as l,w as u,b as g,O as S,l as b,Q as p,t as H}from"./DWUyzV_3.js";import"./A4PA4AXe.js";import"./Be1fzYNM.js";import"./BftmRrQc.js";import"./B3MqGaNe.js";import"./Ds2a9wz8.js";import"./DmfvTtO6.js";var D=$`
    .p-accordionpanel {
        display: flex;
        flex-direction: column;
        border-style: solid;
        border-width: dt('accordion.panel.border.width');
        border-color: dt('accordion.panel.border.color');
    }

    .p-accordionheader {
        all: unset;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: dt('accordion.header.padding');
        color: dt('accordion.header.color');
        background: dt('accordion.header.background');
        border-style: solid;
        border-width: dt('accordion.header.border.width');
        border-color: dt('accordion.header.border.color');
        font-weight: dt('accordion.header.font.weight');
        border-radius: dt('accordion.header.border.radius');
        transition:
            background dt('accordion.transition.duration'),
            color dt('accordion.transition.duration'),
            outline-color dt('accordion.transition.duration'),
            box-shadow dt('accordion.transition.duration');
        outline-color: transparent;
    }

    .p-accordionpanel:first-child > .p-accordionheader {
        border-width: dt('accordion.header.first.border.width');
        border-start-start-radius: dt('accordion.header.first.top.border.radius');
        border-start-end-radius: dt('accordion.header.first.top.border.radius');
    }

    .p-accordionpanel:last-child > .p-accordionheader {
        border-end-start-radius: dt('accordion.header.last.bottom.border.radius');
        border-end-end-radius: dt('accordion.header.last.bottom.border.radius');
    }

    .p-accordionpanel:last-child.p-accordionpanel-active > .p-accordionheader {
        border-end-start-radius: dt('accordion.header.last.active.bottom.border.radius');
        border-end-end-radius: dt('accordion.header.last.active.bottom.border.radius');
    }

    .p-accordionheader-toggle-icon {
        color: dt('accordion.header.toggle.icon.color');
    }

    .p-accordionpanel:not(.p-disabled) .p-accordionheader:focus-visible {
        box-shadow: dt('accordion.header.focus.ring.shadow');
        outline: dt('accordion.header.focus.ring.width') dt('accordion.header.focus.ring.style') dt('accordion.header.focus.ring.color');
        outline-offset: dt('accordion.header.focus.ring.offset');
    }

    .p-accordionpanel:not(.p-accordionpanel-active):not(.p-disabled) > .p-accordionheader:hover {
        background: dt('accordion.header.hover.background');
        color: dt('accordion.header.hover.color');
    }

    .p-accordionpanel:not(.p-accordionpanel-active):not(.p-disabled) .p-accordionheader:hover .p-accordionheader-toggle-icon {
        color: dt('accordion.header.toggle.icon.hover.color');
    }

    .p-accordionpanel:not(.p-disabled).p-accordionpanel-active > .p-accordionheader {
        background: dt('accordion.header.active.background');
        color: dt('accordion.header.active.color');
    }

    .p-accordionpanel:not(.p-disabled).p-accordionpanel-active > .p-accordionheader .p-accordionheader-toggle-icon {
        color: dt('accordion.header.toggle.icon.active.color');
    }

    .p-accordionpanel:not(.p-disabled).p-accordionpanel-active > .p-accordionheader:hover {
        background: dt('accordion.header.active.hover.background');
        color: dt('accordion.header.active.hover.color');
    }

    .p-accordionpanel:not(.p-disabled).p-accordionpanel-active > .p-accordionheader:hover .p-accordionheader-toggle-icon {
        color: dt('accordion.header.toggle.icon.active.hover.color');
    }

    .p-accordioncontent-content {
        border-style: solid;
        border-width: dt('accordion.content.border.width');
        border-color: dt('accordion.content.border.color');
        background-color: dt('accordion.content.background');
        color: dt('accordion.content.color');
        padding: dt('accordion.content.padding');
    }
`,E={root:"p-accordion p-component"},F=x.extend({name:"accordion",style:D,classes:E}),K={name:"BaseAccordion",extends:C,props:{value:{type:[String,Number,Array],default:void 0},multiple:{type:Boolean,default:!1},lazy:{type:Boolean,default:!1},tabindex:{type:Number,default:0},selectOnFocus:{type:Boolean,default:!1},expandIcon:{type:String,default:void 0},collapseIcon:{type:String,default:void 0},activeIndex:{type:[Number,Array],default:null}},style:F,provide:function(){return{$pcAccordion:this,$parentInstance:this}}},z={name:"Accordion",extends:K,inheritAttrs:!1,emits:["update:value","update:activeIndex","tab-open","tab-close","tab-click"],data:function(){return{d_value:this.value}},watch:{value:function(e){this.d_value=e},activeIndex:{immediate:!0,handler:function(e){this.hasAccordionTab&&(this.d_value=this.multiple?e==null?void 0:e.map(String):e==null?void 0:e.toString())}}},methods:{isItemActive:function(e){var o;return this.multiple?(o=this.d_value)===null||o===void 0?void 0:o.includes(e):this.d_value===e},updateValue:function(e){var o,n=this.isItemActive(e);this.multiple?n?this.d_value=this.d_value.filter(function(d){return d!==e}):this.d_value?this.d_value.push(e):this.d_value=[e]:this.d_value=n?null:e,this.$emit("update:value",this.d_value),this.$emit("update:activeIndex",this.multiple?(o=this.d_value)===null||o===void 0?void 0:o.map(Number):Number(this.d_value)),this.$emit(n?"tab-close":"tab-open",{originalEvent:void 0,index:Number(e)})},isAccordionTab:function(e){return e.type.name==="AccordionTab"},getTabProp:function(e,o){return e.props?e.props[o]:void 0},getKey:function(e,o){return this.getTabProp(e,"header")||o},getHeaderPT:function(e,o){var n=this;return{root:s({onClick:function(a){return n.onTabClick(a,o)}},this.getTabProp(e,"headerProps"),this.getTabPT(e,"header",o)),toggleicon:s(this.getTabProp(e,"headeractionprops"),this.getTabPT(e,"headeraction",o))}},getContentPT:function(e,o){return{root:s(this.getTabProp(e,"contentProps"),this.getTabPT(e,"toggleablecontent",o)),transition:this.getTabPT(e,"transition",o),content:this.getTabPT(e,"content",o)}},getTabPT:function(e,o,n){var d=this.tabs.length,a={props:e.props||{},parent:{instance:this,props:this.$props,state:this.$data},context:{index:n,count:d,first:n===0,last:n===d-1,active:this.isItemActive("".concat(n))}};return s(this.ptm("accordiontab.".concat(o),a),this.ptmo(this.getTabProp(e,"pt"),o,a))},onTabClick:function(e,o){this.$emit("tab-click",{originalEvent:e,index:o})}},computed:{tabs:function(){var e=this;return this.$slots.default().reduce(function(o,n){return e.isAccordionTab(n)?o.push(n):n.children&&n.children instanceof Array&&n.children.forEach(function(d){e.isAccordionTab(d)&&o.push(d)}),o},[])},hasAccordionTab:function(){return this.tabs.length}},components:{AccordionPanel:I,AccordionHeader:_,AccordionContent:k,ChevronUpIcon:P,ChevronRightIcon:A}};function L(r,e,o,n,d,a){var m=v("AccordionHeader"),T=v("AccordionContent"),y=v("AccordionPanel");return i(),f("div",s({class:r.cx("root")},r.ptmi("root")),[a.hasAccordionTab?(i(!0),f(B,{key:0},N(a.tabs,function(t,c){return i(),l(y,{key:a.getKey(t,c),value:"".concat(c),pt:{root:a.getTabPT(t,"root",c)},disabled:a.getTabProp(t,"disabled")},{default:u(function(){return[g(m,{class:S(a.getTabProp(t,"headerClass")),pt:a.getHeaderPT(t,c)},{toggleicon:u(function(h){return[h.active?(i(),l(p(r.$slots.collapseicon?r.$slots.collapseicon:r.collapseIcon?"span":"ChevronDownIcon"),s({key:0,class:[r.collapseIcon,h.class],"aria-hidden":"true"},{ref_for:!0},a.getTabPT(t,"headericon",c)),null,16,["class"])):(i(),l(p(r.$slots.expandicon?r.$slots.expandicon:r.expandIcon?"span":"ChevronUpIcon"),s({key:1,class:[r.expandIcon,h.class],"aria-hidden":"true"},{ref_for:!0},a.getTabPT(t,"headericon",c)),null,16,["class"]))]}),default:u(function(){return[t.children&&t.children.headericon?(i(),l(p(t.children.headericon),{key:0,isTabActive:a.isItemActive("".concat(c)),active:a.isItemActive("".concat(c)),index:c},null,8,["isTabActive","active","index"])):b("",!0),t.props&&t.props.header?(i(),f("span",s({key:1,ref_for:!0},a.getTabPT(t,"headertitle",c)),H(t.props.header),17)):b("",!0),t.children&&t.children.header?(i(),l(p(t.children.header),{key:2})):b("",!0)]}),_:2},1032,["class","pt"]),g(T,{pt:a.getContentPT(t,c)},{default:u(function(){return[(i(),l(p(t)))]}),_:2},1032,["pt"])]}),_:2},1032,["value","pt","disabled"])}),128)):w(r.$slots,"default",{key:1})],16)}z.render=L;export{z as default};
