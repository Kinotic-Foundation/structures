import{c as y}from"./Be1fzYNM.js";import{s as v}from"./BOp_CX-A.js";import{v as M,B as w,c as n,o as i,F as h,s as P,P as u,N as s,a as S,O as z,t as g,L as G,l as b,b as f}from"./DIVREvd8.js";var L=M`
    .p-metergroup {
        display: flex;
        gap: dt('metergroup.gap');
    }

    .p-metergroup-meters {
        display: flex;
        background: dt('metergroup.meters.background');
        border-radius: dt('metergroup.border.radius');
    }

    .p-metergroup-label-list {
        display: flex;
        flex-wrap: wrap;
        margin: 0;
        padding: 0;
        list-style-type: none;
    }

    .p-metergroup-label {
        display: inline-flex;
        align-items: center;
        gap: dt('metergroup.label.gap');
    }

    .p-metergroup-label-marker {
        display: inline-flex;
        width: dt('metergroup.label.marker.size');
        height: dt('metergroup.label.marker.size');
        border-radius: 100%;
    }

    .p-metergroup-label-icon {
        font-size: dt('metergroup.label.icon.size');
        width: dt('metergroup.label.icon.size');
        height: dt('metergroup.label.icon.size');
    }

    .p-metergroup-horizontal {
        flex-direction: column;
    }

    .p-metergroup-label-list-horizontal {
        gap: dt('metergroup.label.list.horizontal.gap');
    }

    .p-metergroup-horizontal .p-metergroup-meters {
        height: dt('metergroup.meters.size');
    }

    .p-metergroup-horizontal .p-metergroup-meter:first-of-type {
        border-start-start-radius: dt('metergroup.border.radius');
        border-end-start-radius: dt('metergroup.border.radius');
    }

    .p-metergroup-horizontal .p-metergroup-meter:last-of-type {
        border-start-end-radius: dt('metergroup.border.radius');
        border-end-end-radius: dt('metergroup.border.radius');
    }

    .p-metergroup-vertical {
        flex-direction: row;
    }

    .p-metergroup-label-list-vertical {
        flex-direction: column;
        gap: dt('metergroup.label.list.vertical.gap');
    }

    .p-metergroup-vertical .p-metergroup-meters {
        flex-direction: column;
        width: dt('metergroup.meters.size');
        height: 100%;
    }

    .p-metergroup-vertical .p-metergroup-label-list {
        align-items: flex-start;
    }

    .p-metergroup-vertical .p-metergroup-meter:first-of-type {
        border-start-start-radius: dt('metergroup.border.radius');
        border-start-end-radius: dt('metergroup.border.radius');
    }

    .p-metergroup-vertical .p-metergroup-meter:last-of-type {
        border-end-start-radius: dt('metergroup.border.radius');
        border-end-end-radius: dt('metergroup.border.radius');
    }
`,$={root:function(r){var t=r.props;return["p-metergroup p-component",{"p-metergroup-horizontal":t.orientation==="horizontal","p-metergroup-vertical":t.orientation==="vertical"}]},meters:"p-metergroup-meters",meter:"p-metergroup-meter",labelList:function(r){var t=r.props;return["p-metergroup-label-list",{"p-metergroup-label-list-vertical":t.labelOrientation==="vertical","p-metergroup-label-list-horizontal":t.labelOrientation==="horizontal"}]},label:"p-metergroup-label",labelIcon:"p-metergroup-label-icon",labelMarker:"p-metergroup-label-marker",labelText:"p-metergroup-label-text"},N=w.extend({name:"metergroup",style:L,classes:$}),V={name:"MeterGroup",extends:v,props:{value:{type:Array,default:null},min:{type:Number,default:0},max:{type:Number,default:100},orientation:{type:String,default:"horizontal"},labelPosition:{type:String,default:"end"},labelOrientation:{type:String,default:"horizontal"}},style:N,provide:function(){return{$pcMeterGroup:this,$parentInstance:this}}};function m(e){"@babel/helpers - typeof";return m=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(r){return typeof r}:function(r){return r&&typeof Symbol=="function"&&r.constructor===Symbol&&r!==Symbol.prototype?"symbol":typeof r},m(e)}function C(e,r,t){return(r=T(r))in e?Object.defineProperty(e,r,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[r]=t,e}function T(e){var r=j(e,"string");return m(r)=="symbol"?r:r+""}function j(e,r){if(m(e)!="object"||!e)return e;var t=e[Symbol.toPrimitive];if(t!==void 0){var l=t.call(e,r);if(m(l)!="object")return l;throw new TypeError("@@toPrimitive must return a primitive value.")}return(r==="string"?String:Number)(e)}var k={name:"MeterGroupLabel",hostName:"MeterGroup",extends:v,inheritAttrs:!1,inject:["$pcMeterGroup"],props:{value:{type:Array,default:null},labelPosition:{type:String,default:"end"},labelOrientation:{type:String,default:"horizontal"}},computed:{dataP:function(){return y(C({},this.$pcMeterGroup.labelOrientation,this.$pcMeterGroup.labelOrientation))}}},I=["data-p"];function A(e,r,t,l,O,a){return i(),n("ol",u({class:e.cx("labelList"),"data-p":a.dataP},e.ptm("labelList")),[(i(!0),n(h,null,P(t.value,function(o,p){return i(),n("li",u({key:p+"_label",class:e.cx("label")},{ref_for:!0},e.ptm("label")),[s(e.$slots,"icon",{value:o,class:z(e.cx("labelIcon"))},function(){return[o.icon?(i(),n("i",u({key:0,class:[o.icon,e.cx("labelIcon")],style:{color:o.color}},{ref_for:!0},e.ptm("labelIcon")),null,16)):(i(),n("span",u({key:1,class:e.cx("labelMarker"),style:{backgroundColor:o.color}},{ref_for:!0},e.ptm("labelMarker")),null,16))]}),S("span",u({class:e.cx("labelText")},{ref_for:!0},e.ptm("labelText")),g(o.label)+" ("+g(e.$parentInstance.percentValue(o.value))+")",17)],16)}),128))],16,I)}k.render=A;function d(e){"@babel/helpers - typeof";return d=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(r){return typeof r}:function(r){return r&&typeof Symbol=="function"&&r.constructor===Symbol&&r!==Symbol.prototype?"symbol":typeof r},d(e)}function B(e,r,t){return(r=E(r))in e?Object.defineProperty(e,r,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[r]=t,e}function E(e){var r=F(e,"string");return d(r)=="symbol"?r:r+""}function F(e,r){if(d(e)!="object"||!e)return e;var t=e[Symbol.toPrimitive];if(t!==void 0){var l=t.call(e,r);if(d(l)!="object")return l;throw new TypeError("@@toPrimitive must return a primitive value.")}return(r==="string"?String:Number)(e)}var K={name:"MeterGroup",extends:V,inheritAttrs:!1,methods:{getPTOptions:function(r,t,l){return this.ptm(r,{context:{value:t,index:l}})},percent:function(){var r=arguments.length>0&&arguments[0]!==void 0?arguments[0]:0,t=(r-this.min)/(this.max-this.min)*100;return Math.round(Math.max(0,Math.min(100,t)))},percentValue:function(r){return this.percent(r)+"%"},meterCalculatedStyles:function(r){return{backgroundColor:r.color,width:this.orientation==="horizontal"&&this.percentValue(r.value),height:this.orientation==="vertical"&&this.percentValue(r.value)}}},computed:{totalPercent:function(){return this.percent(this.value.reduce(function(r,t){return r+t.value},0))},percentages:function(){var r=0,t=[];return this.value.forEach(function(l){r+=l.value,t.push(r)}),t},dataP:function(){return y(B({},this.orientation,this.orientation))}},components:{MeterGroupLabel:k}},D=["aria-valuemin","aria-valuemax","aria-valuenow","data-p"],q=["data-p"],H=["data-p"];function J(e,r,t,l,O,a){var o=G("MeterGroupLabel");return i(),n("div",u({class:e.cx("root"),role:"meter","aria-valuemin":e.min,"aria-valuemax":e.max,"aria-valuenow":a.totalPercent,"data-p":a.dataP},e.ptmi("root")),[e.labelPosition==="start"?s(e.$slots,"label",{key:0,value:e.value,totalPercent:a.totalPercent,percentages:a.percentages},function(){return[f(o,{value:e.value,labelPosition:e.labelPosition,labelOrientation:e.labelOrientation,unstyled:e.unstyled,pt:e.pt},null,8,["value","labelPosition","labelOrientation","unstyled","pt"])]}):b("",!0),s(e.$slots,"start",{value:e.value,totalPercent:a.totalPercent,percentages:a.percentages}),S("div",u({class:e.cx("meters"),"data-p":a.dataP},e.ptm("meters")),[(i(!0),n(h,null,P(e.value,function(p,c){return s(e.$slots,"meter",{key:c,value:p,index:c,class:z(e.cx("meter")),orientation:e.orientation,size:a.percentValue(p.value),totalPercent:a.totalPercent},function(){return[a.percent(p.value)?(i(),n("span",u({key:0,class:e.cx("meter"),style:a.meterCalculatedStyles(p),"data-p":a.dataP},{ref_for:!0},a.getPTOptions("meter",p,c)),null,16,H)):b("",!0)]})}),128))],16,q),s(e.$slots,"end",{value:e.value,totalPercent:a.totalPercent,percentages:a.percentages}),e.labelPosition==="end"?s(e.$slots,"label",{key:1,value:e.value,totalPercent:a.totalPercent,percentages:a.percentages},function(){return[f(o,{value:e.value,labelPosition:e.labelPosition,labelOrientation:e.labelOrientation,unstyled:e.unstyled,pt:e.pt},null,8,["value","labelPosition","labelOrientation","unstyled","pt"])]}):b("",!0)],16,D)}K.render=J;export{K as default};
