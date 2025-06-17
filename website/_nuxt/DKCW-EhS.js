import{s as o}from"./BOp_CX-A.js";import{v as r,B as e,c as n,o as p,N as s,P as u}from"./DIVREvd8.js";var a=r`
    .p-buttongroup {
        display: inline-flex;
    }

    .p-buttongroup .p-button {
        margin: 0;
    }

    .p-buttongroup .p-button:not(:last-child),
    .p-buttongroup .p-button:not(:last-child):hover {
        border-inline-end: 0 none;
    }

    .p-buttongroup .p-button:not(:first-of-type):not(:last-of-type) {
        border-radius: 0;
    }

    .p-buttongroup .p-button:first-of-type:not(:only-of-type) {
        border-start-end-radius: 0;
        border-end-end-radius: 0;
    }

    .p-buttongroup .p-button:last-of-type:not(:only-of-type) {
        border-start-start-radius: 0;
        border-end-start-radius: 0;
    }

    .p-buttongroup .p-button:focus {
        position: relative;
        z-index: 1;
    }
`,i={root:"p-buttongroup p-component"},d=e.extend({name:"buttongroup",style:a,classes:i}),l={name:"BaseButtonGroup",extends:o,style:d,provide:function(){return{$pcButtonGroup:this,$parentInstance:this}}},b={name:"ButtonGroup",extends:l,inheritAttrs:!1};function c(t,f,y,g,m,v){return p(),n("span",u({class:t.cx("root"),role:"group"},t.ptmi("root")),[s(t.$slots,"default")],16)}b.render=c;export{b as default};
