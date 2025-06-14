import{c as u}from"./Be1fzYNM.js";import{v as f,B as h,K as g,c as s,o as v,F as y,s as b,P as l,a,N as m,l as k}from"./CwV9-nPG.js";import{s as P}from"./BZ7rLBd_.js";var x=f`
    .p-timeline {
        display: flex;
        flex-grow: 1;
        flex-direction: column;
        direction: ltr;
    }

    .p-timeline-left .p-timeline-event-opposite {
        text-align: right;
    }

    .p-timeline-left .p-timeline-event-content {
        text-align: left;
    }

    .p-timeline-right .p-timeline-event {
        flex-direction: row-reverse;
    }

    .p-timeline-right .p-timeline-event-opposite {
        text-align: left;
    }

    .p-timeline-right .p-timeline-event-content {
        text-align: right;
    }

    .p-timeline-vertical.p-timeline-alternate .p-timeline-event:nth-child(even) {
        flex-direction: row-reverse;
    }

    .p-timeline-vertical.p-timeline-alternate .p-timeline-event:nth-child(odd) .p-timeline-event-opposite {
        text-align: right;
    }

    .p-timeline-vertical.p-timeline-alternate .p-timeline-event:nth-child(odd) .p-timeline-event-content {
        text-align: left;
    }

    .p-timeline-vertical.p-timeline-alternate .p-timeline-event:nth-child(even) .p-timeline-event-opposite {
        text-align: left;
    }

    .p-timeline-vertical.p-timeline-alternate .p-timeline-event:nth-child(even) .p-timeline-event-content {
        text-align: right;
    }

    .p-timeline-vertical .p-timeline-event-opposite,
    .p-timeline-vertical .p-timeline-event-content {
        padding: dt('timeline.vertical.event.content.padding');
    }

    .p-timeline-vertical .p-timeline-event-connector {
        width: dt('timeline.event.connector.size');
    }

    .p-timeline-event {
        display: flex;
        position: relative;
        min-height: dt('timeline.event.min.height');
    }

    .p-timeline-event:last-child {
        min-height: 0;
    }

    .p-timeline-event-opposite {
        flex: 1;
    }

    .p-timeline-event-content {
        flex: 1;
    }

    .p-timeline-event-separator {
        flex: 0;
        display: flex;
        align-items: center;
        flex-direction: column;
    }

    .p-timeline-event-marker {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        position: relative;
        align-self: baseline;
        border-width: dt('timeline.event.marker.border.width');
        border-style: solid;
        border-color: dt('timeline.event.marker.border.color');
        border-radius: dt('timeline.event.marker.border.radius');
        width: dt('timeline.event.marker.size');
        height: dt('timeline.event.marker.size');
        background: dt('timeline.event.marker.background');
    }

    .p-timeline-event-marker::before {
        content: ' ';
        border-radius: dt('timeline.event.marker.content.border.radius');
        width: dt('timeline.event.marker.content.size');
        height: dt('timeline.event.marker.content.size');
        background: dt('timeline.event.marker.content.background');
    }

    .p-timeline-event-marker::after {
        content: ' ';
        position: absolute;
        width: 100%;
        height: 100%;
        border-radius: dt('timeline.event.marker.border.radius');
        box-shadow: dt('timeline.event.marker.content.inset.shadow');
    }

    .p-timeline-event-connector {
        flex-grow: 1;
        background: dt('timeline.event.connector.color');
    }

    .p-timeline-horizontal {
        flex-direction: row;
    }

    .p-timeline-horizontal .p-timeline-event {
        flex-direction: column;
        flex: 1;
    }

    .p-timeline-horizontal .p-timeline-event:last-child {
        flex: 0;
    }

    .p-timeline-horizontal .p-timeline-event-separator {
        flex-direction: row;
    }

    .p-timeline-horizontal .p-timeline-event-connector {
        width: 100%;
        height: dt('timeline.event.connector.size');
    }

    .p-timeline-horizontal .p-timeline-event-opposite,
    .p-timeline-horizontal .p-timeline-event-content {
        padding: dt('timeline.horizontal.event.content.padding');
    }

    .p-timeline-horizontal.p-timeline-alternate .p-timeline-event:nth-child(even) {
        flex-direction: column-reverse;
    }

    .p-timeline-bottom .p-timeline-event {
        flex-direction: column-reverse;
    }
`,w={root:function(t){var i=t.props;return["p-timeline p-component","p-timeline-"+i.align,"p-timeline-"+i.layout]},event:"p-timeline-event",eventOpposite:"p-timeline-event-opposite",eventSeparator:"p-timeline-event-separator",eventMarker:"p-timeline-event-marker",eventConnector:"p-timeline-event-connector",eventContent:"p-timeline-event-content"},z=h.extend({name:"timeline",style:x,classes:w}),S={name:"BaseTimeline",extends:P,props:{value:null,align:{mode:String,default:"left"},layout:{mode:String,default:"vertical"},dataKey:null},style:z,provide:function(){return{$pcTimeline:this,$parentInstance:this}}};function p(e){"@babel/helpers - typeof";return p=typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?function(t){return typeof t}:function(t){return t&&typeof Symbol=="function"&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},p(e)}function c(e,t,i){return(t=T(t))in e?Object.defineProperty(e,t,{value:i,enumerable:!0,configurable:!0,writable:!0}):e[t]=i,e}function T(e){var t=O(e,"string");return p(t)=="symbol"?t:t+""}function O(e,t){if(p(e)!="object"||!e)return e;var i=e[Symbol.toPrimitive];if(i!==void 0){var d=i.call(e,t);if(p(d)!="object")return d;throw new TypeError("@@toPrimitive must return a primitive value.")}return(t==="string"?String:Number)(e)}var _={name:"Timeline",extends:S,inheritAttrs:!1,methods:{getKey:function(t,i){return this.dataKey?g(t,this.dataKey):i},getPTOptions:function(t,i){return this.ptm(t,{context:{index:i,count:this.value.length}})}},computed:{dataP:function(){return u(c(c({},this.layout,this.layout),this.align,this.align))}}},K=["data-p"],C=["data-p"],B=["data-p"],j=["data-p"],N=["data-p"],F=["data-p"],M=["data-p"];function E(e,t,i,d,V,n){return v(),s("div",l({class:e.cx("root")},e.ptmi("root"),{"data-p":n.dataP}),[(v(!0),s(y,null,b(e.value,function(o,r){return v(),s("div",l({key:n.getKey(o,r),class:e.cx("event")},{ref_for:!0},n.getPTOptions("event",r),{"data-p":n.dataP}),[a("div",l({class:e.cx("eventOpposite",{index:r})},{ref_for:!0},n.getPTOptions("eventOpposite",r),{"data-p":n.dataP}),[m(e.$slots,"opposite",{item:o,index:r})],16,B),a("div",l({class:e.cx("eventSeparator")},{ref_for:!0},n.getPTOptions("eventSeparator",r),{"data-p":n.dataP}),[m(e.$slots,"marker",{item:o,index:r},function(){return[a("div",l({class:e.cx("eventMarker")},{ref_for:!0},n.getPTOptions("eventMarker",r),{"data-p":n.dataP}),null,16,N)]}),r!==e.value.length-1?m(e.$slots,"connector",{key:0,item:o,index:r},function(){return[a("div",l({class:e.cx("eventConnector")},{ref_for:!0},n.getPTOptions("eventConnector",r),{"data-p":n.dataP}),null,16,F)]}):k("",!0)],16,j),a("div",l({class:e.cx("eventContent")},{ref_for:!0},n.getPTOptions("eventContent",r),{"data-p":n.dataP}),[m(e.$slots,"content",{item:o,index:r})],16,M)],16,C)}),128))],16,K)}_.render=E;export{_ as default};
