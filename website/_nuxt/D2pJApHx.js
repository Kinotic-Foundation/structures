import{c as d}from"./Be1fzYNM.js";import{s as h}from"./x_TrJ5H1.js";import{s as m}from"./B-kZlFfU.js";import{v as u,B as g,c,l as r,o,N as t,q as s,P as n,Q as l,t as v}from"./DWUyzV_3.js";import"./A4PA4AXe.js";var f=u`
    .p-chip {
        display: inline-flex;
        align-items: center;
        background: dt('chip.background');
        color: dt('chip.color');
        border-radius: dt('chip.border.radius');
        padding-block: dt('chip.padding.y');
        padding-inline: dt('chip.padding.x');
        gap: dt('chip.gap');
    }

    .p-chip-icon {
        color: dt('chip.icon.color');
        font-size: dt('chip.icon.font.size');
        width: dt('chip.icon.size');
        height: dt('chip.icon.size');
    }

    .p-chip-image {
        border-radius: 50%;
        width: dt('chip.image.width');
        height: dt('chip.image.height');
        margin-inline-start: calc(-1 * dt('chip.padding.y'));
    }

    .p-chip:has(.p-chip-remove-icon) {
        padding-inline-end: dt('chip.padding.y');
    }

    .p-chip:has(.p-chip-image) {
        padding-block-start: calc(dt('chip.padding.y') / 2);
        padding-block-end: calc(dt('chip.padding.y') / 2);
    }

    .p-chip-remove-icon {
        cursor: pointer;
        font-size: dt('chip.remove.icon.size');
        width: dt('chip.remove.icon.size');
        height: dt('chip.remove.icon.size');
        color: dt('chip.remove.icon.color');
        border-radius: 50%;
        transition:
            outline-color dt('chip.transition.duration'),
            box-shadow dt('chip.transition.duration');
        outline-color: transparent;
    }

    .p-chip-remove-icon:focus-visible {
        box-shadow: dt('chip.remove.icon.focus.ring.shadow');
        outline: dt('chip.remove.icon.focus.ring.width') dt('chip.remove.icon.focus.ring.style') dt('chip.remove.icon.focus.ring.color');
        outline-offset: dt('chip.remove.icon.focus.ring.offset');
    }
`,y={root:"p-chip p-component",image:"p-chip-image",icon:"p-chip-icon",label:"p-chip-label",removeIcon:"p-chip-remove-icon"},b=g.extend({name:"chip",style:f,classes:y}),k={name:"BaseChip",extends:m,props:{label:{type:[String,Number],default:null},icon:{type:String,default:null},image:{type:String,default:null},removable:{type:Boolean,default:!1},removeIcon:{type:String,default:void 0}},style:b,provide:function(){return{$pcChip:this,$parentInstance:this}}},w={name:"Chip",extends:k,inheritAttrs:!1,emits:["remove"],data:function(){return{visible:!0}},methods:{onKeydown:function(i){(i.key==="Enter"||i.key==="Backspace")&&this.close(i)},close:function(i){this.visible=!1,this.$emit("remove",i)}},computed:{dataP:function(){return d({removable:this.removable})}},components:{TimesCircleIcon:h}},C=["aria-label","data-p"],I=["src"];function z(e,i,B,S,p,a){return p.visible?(o(),c("div",n({key:0,class:e.cx("root"),"aria-label":e.label},e.ptmi("root"),{"data-p":a.dataP}),[t(e.$slots,"default",{},function(){return[e.image?(o(),c("img",n({key:0,src:e.image},e.ptm("image"),{class:e.cx("image")}),null,16,I)):e.$slots.icon?(o(),s(l(e.$slots.icon),n({key:1,class:e.cx("icon")},e.ptm("icon")),null,16,["class"])):e.icon?(o(),c("span",n({key:2,class:[e.cx("icon"),e.icon]},e.ptm("icon")),null,16)):r("",!0),e.label?(o(),c("div",n({key:3,class:e.cx("label")},e.ptm("label")),v(e.label),17)):r("",!0)]}),e.removable?t(e.$slots,"removeicon",{key:0,removeCallback:a.close,keydownCallback:a.onKeydown},function(){return[(o(),s(l(e.removeIcon?"span":"TimesCircleIcon"),n({class:[e.cx("removeIcon"),e.removeIcon],onClick:a.close,onKeydown:a.onKeydown},e.ptm("removeIcon")),null,16,["class","onClick","onKeydown"]))]}):r("",!0)],16,C)):r("",!0)}w.render=z;export{w as default};
